// server.js — RailMatrix API entry point
// Run with: node server.js  OR  npm run dev (nodemon)

const express    = require('express');
const cors       = require('cors');
const rateLimit  = require('express-rate-limit');
require('dotenv').config();

const app = express();

// ── Middleware ────────────────────────────────────────────────────
app.use(cors()); // Allow all origins for now; restrict to your frontend URL in production
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

// Rate limiting — max 150 requests per 15 minutes per IP
const limiter = rateLimit({
    windowMs: 15 * 60 * 1000,
    max: 150,
    standardHeaders: true,
    legacyHeaders: false,
    message: { error: 'Too many requests. Please try again later.' }
});
app.use('/api/', limiter);

// ── Routes ────────────────────────────────────────────────────────
app.use('/api/auth',     require('./routes/auth'));
app.use('/api',          require('./routes/trains'));   // /api/stations/suggest + /api/trains/search
app.use('/api/bookings', require('./routes/bookings'));
app.use('/api/admin',    require('./routes/admin'));

// ── Health check ──────────────────────────────────────────────────
app.get('/', (req, res) => {
    res.json({
        status:  'running',
        service: '🚆 RailMatrix API',
        version: '1.0.0',
        endpoints: {
            auth:     '/api/auth/register | /api/auth/login | /api/auth/verify-email',
            stations: '/api/stations/suggest?q=<partial>',
            trains:   '/api/trains/search?from=X&to=Y&date=YYYY-MM-DD',
            bookings: '/api/bookings/create | /api/bookings/user/:id | /api/bookings/cancel',
            admin:    '/api/admin/login | /api/admin/stats | /api/admin/bookings | /api/admin/users'
        }
    });
});

// ── 404 fallback ──────────────────────────────────────────────────
app.use((req, res) => {
    res.status(404).json({ error: `Route not found: ${req.method} ${req.path}` });
});

// ── Global error handler ──────────────────────────────────────────
app.use((err, req, res, next) => {
    console.error('Unhandled error:', err);
    res.status(500).json({ error: 'Something went wrong. Please try again.' });
});

// ── Start server ──────────────────────────────────────────────────
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log(`\n🚆 RailMatrix API running at http://localhost:${PORT}`);
    console.log(`📋 Endpoints: http://localhost:${PORT}/\n`);
});
