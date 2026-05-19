// routes/trains.js
// GET /api/stations/suggest?q=del          → autocomplete (max 8 results)
// GET /api/trains/search?from=X&to=Y&date=Z → search trains (validates stations first)

const express = require('express');
const router  = express.Router();
const pool    = require('../db');

// ─────────────────────────────────────────────────────────────────
// GET /api/stations/suggest?q=<partial>
// Used for the autocomplete dropdown as user types
// ─────────────────────────────────────────────────────────────────
router.get('/stations/suggest', async (req, res) => {
    const q = (req.query.q || '').trim();

    if (q.length < 1) return res.json({ stations: [] });

    try {
        const [rows] = await pool.query(
            `SELECT station_id, station_name, city
             FROM stations
             WHERE LOWER(station_name) LIKE LOWER(?)
                OR LOWER(city) LIKE LOWER(?)
             ORDER BY
                CASE WHEN LOWER(station_name) LIKE LOWER(?) THEN 0 ELSE 1 END,
                station_name
             LIMIT 8`,
            [`%${q}%`, `%${q}%`, `${q}%`]
        );
        return res.json({ stations: rows });
    } catch (err) {
        console.error('Station suggest error:', err);
        return res.status(500).json({ error: 'Failed to fetch suggestions.' });
    }
});

// ─────────────────────────────────────────────────────────────────
// GET /api/trains/search?from=X&to=Y&date=YYYY-MM-DD
// Mirrors SearchResultGUI.java query exactly.
// Returns 400 if station not found — fixes professor's bug.
// ─────────────────────────────────────────────────────────────────
router.get('/trains/search', async (req, res) => {
    const { from, to, date } = req.query;

    if (!from || !to || !date)
        return res.status(400).json({ error: 'from, to, and date are required.' });

    if (from.toLowerCase() === to.toLowerCase())
        return res.status(400).json({ error: 'Source and destination cannot be the same.' });

    if (!/^\d{4}-\d{2}-\d{2}$/.test(date))
        return res.status(400).json({ error: 'Invalid date. Use YYYY-MM-DD.' });

    const today = new Date().toISOString().split('T')[0];
    if (date < today)
        return res.status(400).json({ error: 'Journey date cannot be in the past.' });

    try {
        // Step 1 — validate FROM station
        const [fromRows] = await pool.query(
            `SELECT station_id, station_name FROM stations
             WHERE LOWER(station_name) LIKE LOWER(?) LIMIT 1`,
            [`%${from}%`]
        );
        if (fromRows.length === 0)
            return res.status(400).json({
                error: `No station found matching "${from}". Please pick from suggestions.`
            });

        // Step 2 — validate TO station
        const [toRows] = await pool.query(
            `SELECT station_id, station_name FROM stations
             WHERE LOWER(station_name) LIKE LOWER(?) LIMIT 1`,
            [`%${to}%`]
        );
        if (toRows.length === 0)
            return res.status(400).json({
                error: `No station found matching "${to}". Please pick from suggestions.`
            });

        const fromId = fromRows[0].station_id;
        const toId   = toRows[0].station_id;

        if (fromId === toId)
            return res.status(400).json({ error: 'Source and destination are the same station.' });

        // Step 3 — find trains (same SQL as SearchResultGUI.java)
        const [trains] = await pool.query(
            `SELECT DISTINCT
                t.train_id, t.train_name, t.train_type,
                t.base_fare, t.fare_per_km, t.total_seats,
                r_from.departure_time  AS dep_time,
                r_to.arrival_time      AS arr_time,
                r_from.stop_number     AS from_stop,
                r_to.stop_number       AS to_stop,
                COALESCE((
                    SELECT SUM(r2.distance_km) FROM routes r2
                    WHERE r2.train_id = t.train_id
                      AND r2.stop_number >= r_from.stop_number
                      AND r2.stop_number <= r_to.stop_number
                ), 0) AS total_km,
                COALESCE(ts.available_seats, t.total_seats) AS available_seats
             FROM trains t
             JOIN routes r_from ON r_from.train_id = t.train_id
                  AND r_from.departure_station_id = ?
             JOIN routes r_to   ON r_to.train_id  = t.train_id
                  AND r_to.destination_station_id  = ?
                  AND r_to.stop_number >= r_from.stop_number
             LEFT JOIN train_schedule ts
                  ON ts.train_id = t.train_id AND ts.journey_date = ?
             ORDER BY dep_time`,
            [fromId, toId, date]
        );

        const results = trains.map(t => {
            const km   = t.total_km || 1;
            const fare = Math.round(parseFloat(t.base_fare) + parseFloat(t.fare_per_km) * km);
            return {
                trainId:        t.train_id,
                trainName:      t.train_name,
                trainType:      t.train_type,
                depTime:        t.dep_time ? String(t.dep_time).substring(0, 5) : null,
                arrTime:        t.arr_time ? String(t.arr_time).substring(0, 5) : null,
                duration:       calcDuration(t.dep_time, t.arr_time),
                distanceKm:     km,
                fare,
                availableSeats: t.available_seats,
                totalSeats:     t.total_seats
            };
        });

        return res.json({
            from: fromRows[0].station_name,
            to:   toRows[0].station_name,
            date, count: results.length, trains: results
        });

    } catch (err) {
        console.error('Train search error:', err);
        return res.status(500).json({ error: 'Search failed. Please try again.' });
    }
});

function calcDuration(dep, arr) {
    if (dep == null || arr == null) return null;
    const toMs = t => {
        if (typeof t === 'number') return t * 1000;
        const p = String(t).split(':');
        return ((+p[0]) * 3600 + (+p[1]) * 60 + (+p[2] || 0)) * 1000;
    };
    let diff = toMs(arr) - toMs(dep);
    if (diff < 0) diff += 24 * 3600 * 1000;
    const mins = Math.floor(diff / 60000);
    return `${Math.floor(mins / 60)}h ${mins % 60}m`;
}

module.exports = router;
