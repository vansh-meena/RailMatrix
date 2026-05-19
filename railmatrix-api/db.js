// db.js — MySQL connection pool
// Connects to the same Railway.app database as the Java app

const mysql = require('mysql2/promise');
require('dotenv').config();

const pool = mysql.createPool({
    host:             process.env.DB_HOST,
    port:             parseInt(process.env.DB_PORT),
    user:             process.env.DB_USER,
    password:         process.env.DB_PASS,
    database:         process.env.DB_NAME,
    ssl:              { rejectUnauthorized: false },
    waitForConnections: true,
    connectionLimit:  10,
    queueLimit:       0,
    timezone:         'Z'
});

// Test connection on startup
pool.getConnection()
    .then(conn => {
        console.log('✅ Connected to Railway.app MySQL database');
        conn.release();
    })
    .catch(err => {
        console.error('❌ Database connection failed:', err.message);
    });

module.exports = pool;
