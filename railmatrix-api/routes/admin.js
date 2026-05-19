// routes/admin.js
// POST /api/admin/login          → admin login (no email verification required)
// GET  /api/admin/stats          → dashboard stats
// GET  /api/admin/bookings       → all bookings
// GET  /api/admin/users          → all users
// GET  /api/admin/trains         → all trains

const express = require('express');
const router  = express.Router();
const crypto  = require('crypto');
const jwt     = require('jsonwebtoken');
const pool    = require('../db');

// SHA-256 — matches AdminDAO.java
function hashPassword(password) {
    return crypto.createHash('sha256').update(password, 'utf8').digest('hex');
}

// Admin JWT middleware (separate from user JWT — checks isAdmin flag)
function adminAuth(req, res, next) {
    const authHeader = req.headers['authorization'];
    if (!authHeader || !authHeader.startsWith('Bearer '))
        return res.status(401).json({ error: 'Admin access required.' });
    try {
        const decoded = jwt.verify(authHeader.split(' ')[1], process.env.JWT_SECRET);
        if (!decoded.isAdmin) return res.status(403).json({ error: 'Not an admin token.' });
        req.admin = decoded;
        next();
    } catch {
        return res.status(401).json({ error: 'Invalid or expired admin token.' });
    }
}

// ─────────────────────────────────────────────────────────────────
// POST /api/admin/login
// Body: { email, password }
// No email verification required for admins
// ─────────────────────────────────────────────────────────────────
router.post('/login', async (req, res) => {
    const { email, password } = req.body;
    if (!email || !password)
        return res.status(400).json({ error: 'Email and password are required.' });

    try {
        const [rows] = await pool.query(
            'SELECT * FROM admins WHERE email = ? AND password = ?',
            [email.trim(), hashPassword(password)]
        );

        if (rows.length === 0)
            return res.status(401).json({ error: 'Invalid admin credentials.' });

        const token = jwt.sign(
            { adminId: rows[0].admin_id, email: rows[0].email, isAdmin: true },
            process.env.JWT_SECRET,
            { expiresIn: '8h' }
        );

        return res.json({ message: 'Admin login successful!', token });

    } catch (err) {
        console.error('Admin login error:', err);
        return res.status(500).json({ error: 'Login failed.' });
    }
});

// ─────────────────────────────────────────────────────────────────
// GET /api/admin/stats  [admin auth]
// Dashboard overview — total users, trains, bookings, revenue
// ─────────────────────────────────────────────────────────────────
router.get('/stats', adminAuth, async (req, res) => {
    try {
        const [[{ totalUsers }]]    = await pool.query('SELECT COUNT(*) AS totalUsers FROM users');
        const [[{ totalTrains }]]   = await pool.query('SELECT COUNT(*) AS totalTrains FROM trains');
        const [[{ totalBookings }]] = await pool.query("SELECT COUNT(*) AS totalBookings FROM bookings WHERE status='ACTIVE'");
        const [[{ totalCancelled }]]= await pool.query("SELECT COUNT(*) AS totalCancelled FROM bookings WHERE status='CANCELLED'");
        const [[{ totalStations }]] = await pool.query('SELECT COUNT(*) AS totalStations FROM stations');

        return res.json({ totalUsers, totalTrains, totalBookings, totalCancelled, totalStations });

    } catch (err) {
        console.error('Stats error:', err);
        return res.status(500).json({ error: 'Failed to fetch stats.' });
    }
});

// ─────────────────────────────────────────────────────────────────
// GET /api/admin/bookings?page=1&limit=20  [admin auth]
// All bookings with user + train info
// ─────────────────────────────────────────────────────────────────
router.get('/bookings', adminAuth, async (req, res) => {
    const page  = Math.max(1, parseInt(req.query.page)  || 1);
    const limit = Math.min(50, parseInt(req.query.limit) || 20);
    const offset = (page - 1) * limit;

    try {
        const [[{ total }]] = await pool.query('SELECT COUNT(*) AS total FROM bookings');

        const [rows] = await pool.query(
            `SELECT b.booking_id, b.journey_date, b.total_passengers,
                    b.status, b.booking_time, b.refund_amount,
                    u.name AS user_name, u.email AS user_email,
                    t.train_name, t.train_type
             FROM bookings b
             JOIN users  u ON u.user_id  = b.user_id
             JOIN trains t ON t.train_id = b.train_id
             ORDER BY b.booking_time DESC
             LIMIT ? OFFSET ?`,
            [limit, offset]
        );

        return res.json({ total, page, limit, bookings: rows });

    } catch (err) {
        console.error('Admin bookings error:', err);
        return res.status(500).json({ error: 'Failed to fetch bookings.' });
    }
});

// ─────────────────────────────────────────────────────────────────
// GET /api/admin/users  [admin auth]
// All registered users
// ─────────────────────────────────────────────────────────────────
router.get('/users', adminAuth, async (req, res) => {
    try {
        const [rows] = await pool.query(
            `SELECT u.user_id, u.name, u.email, u.is_verified,
                    COUNT(b.booking_id) AS total_bookings
             FROM users u
             LEFT JOIN bookings b ON b.user_id = u.user_id
             GROUP BY u.user_id
             ORDER BY u.user_id DESC`
        );
        return res.json({ count: rows.length, users: rows });
    } catch (err) {
        console.error('Admin users error:', err);
        return res.status(500).json({ error: 'Failed to fetch users.' });
    }
});

// ─────────────────────────────────────────────────────────────────
// GET /api/admin/trains  [admin auth]
// All trains with station info
// ─────────────────────────────────────────────────────────────────
router.get('/trains', adminAuth, async (req, res) => {
    try {
        const [rows] = await pool.query(
            `SELECT t.train_id, t.train_name, t.train_type,
                    t.total_seats, t.base_fare, t.fare_per_km,
                    COUNT(DISTINCT r.route_id) AS route_stops
             FROM trains t
             LEFT JOIN routes r ON r.train_id = t.train_id
             GROUP BY t.train_id
             ORDER BY t.train_id`
        );
        return res.json({ count: rows.length, trains: rows });
    } catch (err) {
        console.error('Admin trains error:', err);
        return res.status(500).json({ error: 'Failed to fetch trains.' });
    }
});

module.exports = router;
