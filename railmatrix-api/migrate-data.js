const fs = require('fs');
const path = require('path');
const mysql = require('mysql2/promise');
require('dotenv').config();

async function migrate() {
    console.log('Connecting to database...');
    const conn = await mysql.createConnection({
        host:             process.env.DB_HOST,
        port:             parseInt(process.env.DB_PORT),
        user:             process.env.DB_USER,
        password:         process.env.DB_PASS,
        database:         process.env.DB_NAME,
        ssl:              { rejectUnauthorized: false },
        multipleStatements: true,
        timezone:         'Z'
    });
    console.log('✅ Connected.');

    try {
        // 0. CLEAR TABLES
        console.log('Clearing old data...');
        await conn.query('SET FOREIGN_KEY_CHECKS = 0');
        await conn.query('TRUNCATE TABLE routes');
        await conn.query('TRUNCATE TABLE schedule_seats');
        await conn.query('TRUNCATE TABLE train_classes');
        await conn.query('TRUNCATE TABLE trains');
        await conn.query('TRUNCATE TABLE stations');
        // Let's also clear bookings and passengers so it's a clean state
        await conn.query('TRUNCATE TABLE passengers');
        await conn.query('TRUNCATE TABLE bookings');
        await conn.query('SET FOREIGN_KEY_CHECKS = 1');
        console.log('✅ Tables cleared.');

        // 1. STATIONS
        console.log('Importing stations...');
        const stationsSql = fs.readFileSync(path.join(__dirname, '../src/com/smartrail/database/stations.sql'), 'utf8');
        await conn.query(stationsSql);
        console.log('✅ Stations imported.');

        // 2. TRAINS
        console.log('Importing trains...');
        const trainsRaw = fs.readFileSync(path.join(__dirname, '../src/com/smartrail/database/trains.sql'), 'utf8');
        
        // Extract all lines that look like: ('Name', 'Type', 'Dep', 'Dest', seats, fare, fare_km)
        const trainRegex = /\('([^']*)',\s*'([^']*)',\s*'([^']*)',\s*'([^']*)',\s*\d+,\s*([\d.]+),\s*([\d.]+)\)/g;
        let match;
        const insertedTrains = [];
        
        while ((match = trainRegex.exec(trainsRaw)) !== null) {
            const name = match[1];
            const type = match[2];
            const dep = match[3];
            const dest = match[4];
            
            // Adjust fares to be reasonable
            let baseFare = parseFloat(match[5]);
            if (baseFare > 150) baseFare = Math.floor(Math.random() * 50) + 100; // 100-150
            if (baseFare < 50) baseFare = 50;
            
            let fareKm = parseFloat(match[6]);
            if (fareKm > 1.0) fareKm = 0.8 + (Math.random() * 0.2); // 0.8 - 1.0

            const [result] = await conn.query(
                `INSERT INTO trains (train_name, train_type, departure, destination, base_fare, fare_per_km) VALUES (?, ?, ?, ?, ?, ?)`,
                [name, type, dep, dest, baseFare.toFixed(2), fareKm.toFixed(2)]
            );
            
            const trainId = result.insertId;
            insertedTrains.push(trainId);
        }
        console.log(`✅ ${insertedTrains.length} Trains imported with adjusted fares.`);

        // 3. TRAIN CLASSES & SCHEDULE SEATS (batch inserts for speed)
        console.log('Generating classes and schedules...');
        
        const classes = [
            { code: '1AC', seats: 48 },
            { code: '2AC', seats: 108 },
            { code: '3AC', seats: 192 },
            { code: 'SL', seats: 360 }
        ];

        // Pre-compute date strings
        const dates = [];
        for (let d = 0; d < 30; d++) {
            const dDate = new Date();
            dDate.setDate(dDate.getDate() + d);
            dates.push(dDate.toISOString().split('T')[0]);
        }

        for (const tid of insertedTrains) {
            // Batch insert train_classes
            const classRows = classes.map(c => [tid, c.code, c.seats]);
            await conn.query(`INSERT INTO train_classes (train_id, class_code, total_seats) VALUES ?`, [classRows]);

            // Batch insert schedule_seats — all classes × all 30 days at once
            const schedRows = [];
            for (const c of classes) {
                const gn = Math.floor(c.seats * 0.80);
                const tq = Math.floor(c.seats * 0.10);
                const ld = Math.floor(c.seats * 0.05);
                const hq = c.seats - (gn + tq + ld);
                for (const ds of dates) {
                    schedRows.push([tid, ds, c.code, gn, tq, ld, hq]);
                }
            }
            await conn.query(
                `INSERT INTO schedule_seats (train_id, journey_date, class_code, available_gn, available_tq, available_ld, available_hq) VALUES ?`,
                [schedRows]
            );
        }
        console.log('✅ Classes and 30-day schedules generated for all trains.');

        // 4. ROUTES
        console.log('Importing routes (this may take a few seconds)...');
        const routesSql = fs.readFileSync(path.join(__dirname, '../src/com/smartrail/database/routes.sql'), 'utf8');
        await conn.query(routesSql);
        console.log('✅ Routes imported.');

    } catch (err) {
        console.error('Migration failed:', err);
    } finally {
        await conn.end();
    }
}

migrate();
