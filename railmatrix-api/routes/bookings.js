// routes/bookings.js
// POST /api/bookings/create          → book a ticket
// GET  /api/bookings/user/:userId    → get all bookings for a user
// POST /api/bookings/cancel          → cancel a booking
// GET  /api/bookings/:bookingId      → get single booking detail

const express  = require('express');
const router   = express.Router();
const pool     = require('../db');
const auth     = require('../middleware/auth');
const nodemailer = require('nodemailer');

// ── Nodemailer (same Gmail SMTP as EmailService.java) ─────────────
function createTransporter() {
    return nodemailer.createTransport({
        host: 'smtp.gmail.com', port: 465, secure: true,
        auth: { user: process.env.EMAIL_FROM, pass: process.env.EMAIL_PASS }
    });
}

// ─────────────────────────────────────────────────────────────────
// POST /api/bookings/create  [auth required]
// Body: { trainId, journeyDate, passengers: [{name,age,gender}] }
// ─────────────────────────────────────────────────────────────────
router.post('/create', auth, async (req, res) => {
    const { trainId, journeyDate, passengers } = req.body;
    const userId = req.user.userId;

    if (!trainId || !journeyDate || !passengers || passengers.length === 0)
        return res.status(400).json({ error: 'trainId, journeyDate, and passengers are required.' });

    if (passengers.length > 6)
        return res.status(400).json({ error: 'Maximum 6 passengers per booking.' });

    const conn = await pool.getConnection();
    try {
        await conn.beginTransaction();

        // 1. Check available seats via schedule_seats (sum all quota columns)
        const [schedRows] = await conn.query(
            `SELECT COALESCE(SUM(available_gn + available_tq + available_ld + available_hq), 0) AS available_seats
             FROM schedule_seats WHERE train_id = ? AND journey_date = ?`,
            [trainId, journeyDate]
        );

        // If no schedule_seats row yet, fall back to total train capacity
        const [capacityRows] = await conn.query(
            'SELECT COALESCE(SUM(total_seats),0) AS cap FROM train_classes WHERE train_id = ?',
            [trainId]
        );
        const available = schedRows[0]?.available_seats ?? capacityRows[0]?.cap ?? 0;

        if (available < passengers.length) {
            await conn.rollback();
            return res.status(400).json({
                error: `Only ${available} seat(s) available. Requested: ${passengers.length}.`
            });
        }

        // 2. Create booking with PNR
        const pnr = generatePNR();
        const [bookingResult] = await conn.query(
            'INSERT INTO bookings (pnr, user_id, train_id, journey_date, total_passengers) VALUES (?, ?, ?, ?, ?)',
            [pnr, userId, trainId, journeyDate, passengers.length]
        );
        const bookingId = bookingResult.insertId;

        // 3. Insert passengers (table may be booking_passengers or passengers)
        for (const p of passengers) {
            if (!p.name || !p.age || !p.gender)
                throw new Error('Each passenger must have name, age, and gender.');
            // Try booking_passengers first, fallback column name passenger_name
            await conn.query(
                'INSERT INTO booking_passengers (booking_id, passenger_name, age, gender) VALUES (?, ?, ?, ?)',
                [bookingId, p.name.trim(), parseInt(p.age), p.gender]
            );
        }

        // 4. Deduct seats from schedule_seats GN quota (upsert pattern)
        const deduct = passengers.length;
        const [ssRows] = await conn.query(
            'SELECT id FROM schedule_seats WHERE train_id = ? AND journey_date = ? LIMIT 1',
            [trainId, journeyDate]
        );
        if (ssRows.length > 0) {
            await conn.query(
                `UPDATE schedule_seats
                 SET available_gn = GREATEST(available_gn - ?, 0)
                 WHERE train_id = ? AND journey_date = ?`,
                [deduct, trainId, journeyDate]
            );
        }
        // If no row exists yet, seats are still conceptually available from train_classes

        await conn.commit();

        // 5. Fetch train info for email
        const [trainRows] = await pool.query(
            `SELECT t.train_name, t.train_type,
                    s1.station_name AS dep_station,
                    s2.station_name AS arr_station,
                    t.base_fare, t.fare_per_km,
                    COALESCE((SELECT SUM(r2.distance_km) FROM routes r2 WHERE r2.train_id = t.train_id), 0) AS total_km
             FROM trains t
             LEFT JOIN routes r ON r.train_id = t.train_id AND r.stop_number = 1
             LEFT JOIN stations s1 ON s1.station_id = r.departure_station_id
             LEFT JOIN stations s2 ON s2.station_id = r.destination_station_id
             WHERE t.train_id = ? LIMIT 1`,
            [trainId]
        );

        const [userRows] = await pool.query('SELECT name, email FROM users WHERE user_id = ?', [userId]);
        const train = trainRows[0] || {};
        const user  = userRows[0] || {};
        const { calculateFare } = require('../utils/fare');
        const farePerPax = calculateFare({
            baseFare: train.base_fare || 50,
            farePerKm: train.fare_per_km || 1,
            distanceKm: train.total_km || 1,
            trainType: train.train_type || 'Express',
            classCode: 'SL'
        });
        const fare = farePerPax * passengers.length;

        // 6. Send confirmation email (non-blocking)
        sendBookingEmail(user.email, user.name, bookingId, train.train_name, train.dep_station, train.arr_station, journeyDate, passengers.length, fare).catch(e => console.error('Email error:', e.message));

        return res.status(201).json({
            message: 'Booking confirmed!',
            bookingId,
            pnr,
            trainName:    train.train_name,
            journeyDate,
            totalPassengers: passengers.length,
            totalFare: fare
        });

    } catch (err) {
        await conn.rollback();
        console.error('Booking error:', err);
        return res.status(500).json({ error: err.message || 'Booking failed.' });
    } finally {
        conn.release();
    }
});

// ─────────────────────────────────────────────────────────────────
// GET /api/bookings/user/:userId  [auth required]
// Returns all bookings (active + cancelled) for the logged-in user
// ─────────────────────────────────────────────────────────────────
router.get('/user/:userId', auth, async (req, res) => {
    const userId = parseInt(req.params.userId);

    // Users can only see their own bookings
    if (req.user.userId !== userId)
        return res.status(403).json({ error: 'Access denied.' });

    try {
        const [rows] = await pool.query(
            `SELECT
                b.booking_id, b.journey_date, b.total_passengers,
                b.status, b.booking_time, b.cancelled_at, b.refund_amount,
                t.train_id, t.train_name, t.train_type,
                t.base_fare, t.fare_per_km,
                COALESCE((
                    SELECT SUM(r2.distance_km) FROM routes r2 WHERE r2.train_id = t.train_id
                ), 0) AS total_km
             FROM bookings b
             JOIN trains t ON t.train_id = b.train_id
             WHERE b.user_id = ?
             ORDER BY b.booking_time DESC`,
            [userId]
        );

        const bookings = rows.map(b => ({
            bookingId:       b.booking_id,
            journeyDate:     b.journey_date,
            totalPassengers: b.total_passengers,
            status:          b.status,
            bookingTime:     b.booking_time,
            cancelledAt:     b.cancelled_at,
            refundAmount:    b.refund_amount,
            trainId:         b.train_id,
            trainName:       b.train_name,
            trainType:       b.train_type,
            totalFare:       Math.round((parseFloat(b.base_fare) + parseFloat(b.fare_per_km) * (b.total_km || 1)) * b.total_passengers)
        }));

        return res.json({ count: bookings.length, bookings });

    } catch (err) {
        console.error('Get bookings error:', err);
        return res.status(500).json({ error: 'Failed to fetch bookings.' });
    }
});

// ─────────────────────────────────────────────────────────────────
// GET /api/bookings/pnr/:pnr  [public access for PNR check]
// Single booking by PNR with full passenger list
// ─────────────────────────────────────────────────────────────────
router.get('/pnr/:pnr', async (req, res) => {
    const pnr = req.params.pnr;
    try {
        const [rows] = await pool.query(
            `SELECT b.*,
                t.train_name, t.train_type,
                src.station_name AS source_station, src.city AS source_city,
                dest.station_name AS dest_station, dest.city AS dest_city,
                u.email
             FROM bookings b
             JOIN trains t ON b.train_id = t.train_id
             JOIN routes r1 ON t.train_id = r1.train_id AND r1.stop_number = 1
             JOIN routes r2 ON t.train_id = r2.train_id
                  AND r2.stop_number = (SELECT MAX(stop_number) FROM routes WHERE train_id = t.train_id)
             JOIN stations src  ON src.station_id  = r1.departure_station_id
             JOIN stations dest ON dest.station_id = r2.destination_station_id
             JOIN users u ON b.user_id = u.user_id
             WHERE b.pnr = ?`,
            [pnr]
        );

        if (rows.length === 0)
            return res.status(404).json({ error: 'Invalid PNR or booking not found.' });

        const booking = rows[0];

        // Fetch passengers from booking_passengers
        const [passengers] = await pool.query(
            `SELECT passenger_id, passenger_name, age, gender,
                    COALESCE(seat_number, 'TBD') AS seat_number,
                    COALESCE(status, 'CNF') AS status,
                    COALESCE(wl_position, 0) AS wl_position
             FROM booking_passengers WHERE booking_id = ?`,
            [booking.booking_id]
        );

        return res.json({
            bookingId:       booking.booking_id,
            pnr:             booking.pnr,
            status:          booking.status,
            journeyDate:     booking.journey_date,
            bookingTime:     booking.booking_time,
            classCode:       booking.class_code  || 'SL',
            quotaCode:       booking.quota_code  || 'GN',
            totalPassengers: booking.total_passengers,
            trainName:       booking.train_name,
            departure:       booking.source_city  || booking.source_station,
            destination:     booking.dest_city    || booking.dest_station,
            passengers
        });

    } catch (err) {
        console.error('Get by PNR error:', err);
        return res.status(500).json({ error: 'Database error fetching PNR.' });
    }
});

// ─────────────────────────────────────────────────────────────────
// GET /api/bookings/:bookingId  [auth required]
// Single booking with full passenger list
// ─────────────────────────────────────────────────────────────────
router.get('/:bookingId', auth, async (req, res) => {
    const bookingId = parseInt(req.params.bookingId);

    try {
        const [bookingRows] = await pool.query(
            `SELECT b.*, t.train_name, t.train_type, t.base_fare, t.fare_per_km,
                    COALESCE((SELECT SUM(r2.distance_km) FROM routes r2 WHERE r2.train_id=t.train_id),0) AS total_km
             FROM bookings b JOIN trains t ON t.train_id=b.train_id
             WHERE b.booking_id = ?`,
            [bookingId]
        );

        if (bookingRows.length === 0)
            return res.status(404).json({ error: 'Booking not found.' });

        const b = bookingRows[0];

        // Only the owner or admin can see
        if (req.user.userId !== b.user_id)
            return res.status(403).json({ error: 'Access denied.' });

        const [passengers] = await pool.query(
            'SELECT passenger_name, age, gender, seat_number FROM passengers WHERE booking_id = ?',
            [bookingId]
        );

        return res.json({
            bookingId:       b.booking_id,
            status:          b.status,
            journeyDate:     b.journey_date,
            bookingTime:     b.booking_time,
            refundAmount:    b.refund_amount,
            cancelledAt:     b.cancelled_at,
            totalPassengers: b.total_passengers,
            totalFare:       Math.round((parseFloat(b.base_fare) + parseFloat(b.fare_per_km) * (b.total_km || 1)) * b.total_passengers),
            train: { id: b.train_id, name: b.train_name, type: b.train_type },
            passengers
        });

    } catch (err) {
        console.error('Get booking error:', err);
        return res.status(500).json({ error: 'Failed to fetch booking.' });
    }
});

// ─────────────────────────────────────────────────────────────────
// POST /api/bookings/cancel  [auth required]
// Body: { bookingId }
// Refund policy mirrors CancelBookingGUI.java:
//   >7 days  → 90%  refund
//   >3 days  → 50%  refund
//   >1 day   → 25%  refund
//   ≤1 day   → 0%   refund
// ─────────────────────────────────────────────────────────────────
router.post('/cancel', auth, async (req, res) => {
    const { bookingId } = req.body;
    if (!bookingId) return res.status(400).json({ error: 'bookingId is required.' });

    const conn = await pool.getConnection();
    try {
        await conn.beginTransaction();

        const [rows] = await conn.query(
            `SELECT b.*, t.train_name, t.train_type, t.base_fare, t.fare_per_km,
                    COALESCE((SELECT SUM(r2.distance_km) FROM routes r2 WHERE r2.train_id=t.train_id),0) AS total_km
             FROM bookings b JOIN trains t ON t.train_id = b.train_id
             WHERE b.booking_id = ?`,
            [bookingId]
        );

        if (rows.length === 0) {
            await conn.rollback();
            return res.status(404).json({ error: 'Booking not found.' });
        }

        const b = rows[0];

        if (req.user.userId !== b.user_id) {
            await conn.rollback();
            return res.status(403).json({ error: 'Access denied.' });
        }

        if (b.status === 'CANCELLED') {
            await conn.rollback();
            return res.status(400).json({ error: 'Booking is already cancelled.' });
        }

        // Calculate refund (same policy as CancelBookingGUI.java)
        const daysUntilJourney = Math.floor((new Date(b.journey_date) - new Date()) / (1000 * 60 * 60 * 24));
        let refundPct = 0;
        if      (daysUntilJourney > 7) refundPct = 0.90;
        else if (daysUntilJourney > 3) refundPct = 0.50;
        else if (daysUntilJourney > 1) refundPct = 0.25;

        const { calculateFare } = require('../utils/fare');
        const farePerPax = calculateFare({
            baseFare: b.base_fare || 50, farePerKm: b.fare_per_km || 1,
            distanceKm: b.total_km || 1, trainType: b.train_type || 'Express', classCode: 'SL'
        });
        const totalFare   = farePerPax * b.total_passengers;
        const refundAmount = Math.round(totalFare * refundPct);

        // Mark cancelled
        await conn.query(
            `UPDATE bookings SET status='CANCELLED', cancelled_at=CURRENT_TIMESTAMP, refund_amount=?
             WHERE booking_id=?`,
            [refundAmount, bookingId]
        );

        // Restore seats in schedule_seats if a row exists
        await conn.query(
            `UPDATE schedule_seats SET available_gn = available_gn + ?
             WHERE train_id=? AND journey_date=?`,
            [b.total_passengers, b.train_id, b.journey_date]
        );

        await conn.commit();

        // Fetch user for email
        const [userRows] = await pool.query('SELECT name, email FROM users WHERE user_id=?', [b.user_id]);
        const user = userRows[0] || {};
        sendCancellationEmail(user.email, user.name, bookingId, b.train_name, b.journey_date, refundAmount).catch(e => console.error('Email error:', e.message));

        return res.json({
            message:       'Booking cancelled successfully.',
            bookingId,
            refundAmount,
            refundPercent: refundPct * 100,
            daysUntilJourney
        });

    } catch (err) {
        await conn.rollback();
        console.error('Cancel error:', err);
        return res.status(500).json({ error: 'Cancellation failed.' });
    } finally {
        conn.release();
    }
});

// ── Email helpers — match EmailService.java templates ─────────────
async function sendBookingEmail(toEmail, userName, bookingId, trainName, departure, destination, journeyDate, passengers, totalFare) {
    if (!toEmail) return;
    const html = `<!DOCTYPE html><html><head><meta charset="UTF-8"><style>
        body{font-family:'Helvetica Neue',Arial,sans-serif;background:#f5f0ff;margin:0;padding:20px}
        .c{max-width:560px;margin:0 auto;background:white;border-radius:16px;overflow:hidden;box-shadow:0 4px 20px rgba(72,52,120,.15)}
        .h{background:linear-gradient(135deg,#32235a,#483478);padding:28px 32px}
        .h h1{color:white;margin:0;font-size:22px}.h p{color:#c8b8e8;margin:4px 0 0;font-size:14px}
        .b{padding:28px 32px}.bid{text-align:center;background:#ede5ff;border-radius:8px;padding:12px;margin:16px 0}
        .bid span{font-size:22px;font-weight:bold;color:#483478;letter-spacing:2px}
        .row{display:flex;justify-content:space-between;padding:6px 0;border-bottom:1px solid #ede5ff}
        .lbl{color:#888;font-size:13px}.val{color:#32235a;font-size:13px;font-weight:bold}
        .total{background:#483478;border-radius:10px;padding:16px 20px;display:flex;justify-content:space-between;align-items:center;margin:16px 0}
        .tl{color:#c8b8e8;font-size:14px}.tv{color:white;font-size:24px;font-weight:bold}
        .f{background:#f8f4ff;padding:16px 32px;text-align:center;color:#aaa;font-size:12px}
    </style></head><body><div class="c">
        <div class="h"><h1>🚆 RailMatrix</h1><p>Your booking is confirmed!</p></div>
        <div class="b">
            <p>Hello <strong>${userName || 'Traveller'}</strong>,<br>Your ticket has been booked successfully.</p>
            <div class="bid">Booking ID &nbsp;<span>#${bookingId}</span></div>
            <p style="text-align:center;font-size:18px;font-weight:bold;color:#483478">${departure || ''} → ${destination || ''}</p>
            <div style="background:#f8f4ff;border:1px solid #dcd0ed;border-radius:12px;padding:20px">
                <div class="row"><span class="lbl">Train</span><span class="val">${trainName || ''}</span></div>
                <div class="row"><span class="lbl">Journey Date</span><span class="val">${journeyDate}</span></div>
                <div class="row"><span class="lbl">Passengers</span><span class="val">${passengers}</span></div>
            </div>
            <div class="total"><span class="tl">Total Paid</span><span class="tv">₹${totalFare}</span></div>
        </div>
        <div class="f">© 2026 RailMatrix · Do not reply to this email.</div>
    </div></body></html>`;
    await createTransporter().sendMail({
        from: `"${process.env.EMAIL_FROM_NAME}" <${process.env.EMAIL_FROM}>`,
        to: toEmail,
        subject: `Booking Confirmed! #${bookingId} — ${trainName}`,
        html
    });
}

async function sendCancellationEmail(toEmail, userName, bookingId, trainName, journeyDate, refundAmount) {
    if (!toEmail) return;
    const html = `<!DOCTYPE html><html><head><meta charset="UTF-8"><style>
        body{font-family:'Helvetica Neue',Arial,sans-serif;background:#f5f0ff;margin:0;padding:20px}
        .c{max-width:560px;margin:0 auto;background:white;border-radius:16px;overflow:hidden;box-shadow:0 4px 20px rgba(72,52,120,.15)}
        .h{background:linear-gradient(135deg,#8b0000,#c0392b);padding:28px 32px}
        .h h1{color:white;margin:0;font-size:22px}.h p{color:#f5b8b8;margin:4px 0 0;font-size:14px}
        .b{padding:28px 32px}
        .row{display:flex;justify-content:space-between;padding:6px 0;border-bottom:1px solid #ffe0e0}
        .lbl{color:#888;font-size:13px}.val{color:#32235a;font-size:13px;font-weight:bold}
        .ref{background:#28a050;border-radius:10px;padding:16px 20px;display:flex;justify-content:space-between;align-items:center;margin:16px 0}
        .rl{color:#c8f0d8;font-size:14px}.rv{color:white;font-size:24px;font-weight:bold}
        .f{background:#f8f4ff;padding:16px 32px;text-align:center;color:#aaa;font-size:12px}
    </style></head><body><div class="c">
        <div class="h"><h1>🚆 RailMatrix</h1><p>Booking Cancelled</p></div>
        <div class="b">
            <p>Hello <strong>${userName || 'Traveller'}</strong>,<br>Your booking has been cancelled.</p>
            <div style="background:#fff5f5;border:1px solid #f5c6c6;border-radius:12px;padding:20px">
                <div class="row"><span class="lbl">Booking ID</span><span class="val">#${bookingId}</span></div>
                <div class="row"><span class="lbl">Train</span><span class="val">${trainName || ''}</span></div>
                <div class="row"><span class="lbl">Journey Date</span><span class="val">${journeyDate}</span></div>
            </div>
            <div class="ref"><span class="rl">Refund Amount</span><span class="rv">₹${refundAmount}</span></div>
            <p style="color:#888;font-size:13px;text-align:center">Refund will be credited within 5-7 business days.</p>
        </div>
        <div class="f">© 2026 RailMatrix · Do not reply to this email.</div>
    </div></body></html>`;
    await createTransporter().sendMail({
        from: `"${process.env.EMAIL_FROM_NAME}" <${process.env.EMAIL_FROM}>`,
        to: toEmail,
        subject: `Booking Cancelled #${bookingId} — Refund: ₹${refundAmount}`,
        html
    });
}

// ── PNR Generator: RM + YY + MM + 6 random digits ─────────────────
function generatePNR() {
    const now = new Date();
    const yy  = String(now.getFullYear()).slice(2);
    const mm  = String(now.getMonth() + 1).padStart(2, '0');
    const rnd = String(Math.floor(100000 + Math.random() * 900000));
    return `RM${yy}${mm}${rnd}`;
}

module.exports = router;
