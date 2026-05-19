require('dotenv').config();
const crypto = require('crypto');
const pool = require('./db');

function hashPassword(password) {
    return crypto.createHash('sha256').update(password, 'utf8').digest('hex');
}

async function seed() {
    try {
        console.log('Seeding Database...');

        // Seed Admin
        const adminEmail = 'admin@railmatrix.com';
        const adminPass = 'admin123';
        const hash = hashPassword(adminPass);

        await pool.query('INSERT IGNORE INTO admins (email, password) VALUES (?, ?)', [adminEmail, hash]);
        console.log(`✅ Admin seeded: ${adminEmail} / ${adminPass}`);

        console.log('Seeding Complete!');
        process.exit(0);
    } catch (err) {
        console.error('Seeding error:', err);
        process.exit(1);
    }
}

seed();
