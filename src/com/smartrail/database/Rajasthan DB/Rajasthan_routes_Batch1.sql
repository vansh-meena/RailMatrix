-- ================================================================
-- RAJASTHAN ROUTES — BATCH 1 (Jaipur Based)
-- ================================================================

-- ----------------------------------------------------------------
-- Jaipur Delhi Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Delhi Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Dausa Junction'),
  1, 55, 50, NULL, '06:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Delhi Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Dausa Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bandikui Junction'),
  2, 35, 30, '06:55:00', '07:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Delhi Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Bandikui Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  3, 90, 75, '07:30:00', '07:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Delhi Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  4, 160, 130, '08:50:00', NULL
);

-- ----------------------------------------------------------------
-- Jaipur Mumbai Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  1, 135, 100, NULL, '14:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  2, 200, 160, '16:50:00', '17:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  3, 220, 180, '19:40:00', '19:50:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  4, 265, 200, '23:10:00', '23:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  5, 263, 180, '02:20:00', NULL
);

-- ----------------------------------------------------------------
-- Jaipur Jodhpur Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kishangarh Junction'),
  1, 90, 75, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Kishangarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  2, 45, 40, '07:15:00', '07:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Beawar Junction'),
  3, 55, 50, '08:00:00', '08:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Beawar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  4, 120, 100, '08:55:00', '09:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  5, 80, 70, '10:40:00', NULL
);

-- ----------------------------------------------------------------
-- Jaipur Bikaner Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Bikaner Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Sikar Junction'),
  1, 110, 90, NULL, '07:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Bikaner Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Sikar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ratangarh Junction'),
  2, 100, 85, '08:30:00', '08:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Bikaner Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ratangarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bikaner Junction'),
  3, 120, 100, '10:00:00', NULL
);

-- ----------------------------------------------------------------
-- Jaipur Ahmedabad Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Ahmedabad Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  1, 135, 100, NULL, '15:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Ahmedabad Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  2, 200, 160, '17:20:00', '17:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Ahmedabad Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  3, 220, 180, '20:10:00', NULL
);

-- ----------------------------------------------------------------
-- Jaipur Lucknow Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Lucknow Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bandikui Junction'),
  1, 90, 75, NULL, '18:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Lucknow Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bandikui Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mathura Junction'),
  2, 180, 150, '19:15:00', '19:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Lucknow Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Mathura Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Agra Cantt'),
  3, 58, 45, '21:50:00', '21:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Lucknow Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Agra Cantt'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  4, 280, 210, '22:40:00', '22:50:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Lucknow Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Lucknow Charbagh'),
  5, 75, 60, '02:20:00', NULL
);

-- ----------------------------------------------------------------
-- Jaipur Kolkata Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Kolkata Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Sawai Madhopur Junction'),
  1, 130, 105, NULL, '20:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Kolkata Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Sawai Madhopur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  2, 110, 90, '21:45:00', '21:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Kolkata Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  3, 400, 300, '00:55:00', '01:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Kolkata Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Varanasi Junction'),
  4, 120, 90, '06:05:00', '06:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Kolkata Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Varanasi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  5, 250, 180, '08:45:00', '08:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Kolkata Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  6, 530, 360, '12:55:00', NULL
);

-- ----------------------------------------------------------------
-- Ajmer Jaipur Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kishangarh Junction'),
  1, 45, 40, NULL, '07:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Kishangarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Phulera Junction'),
  2, 60, 50, '07:40:00', '07:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Phulera Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  3, 45, 40, '08:35:00', NULL
);

-- ----------------------------------------------------------------
-- Kota Jaipur Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bundi Junction'),
  1, 40, 35, NULL, '06:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Bundi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Sawai Madhopur Junction'),
  2, 80, 70, '07:05:00', '07:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Sawai Madhopur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Gangapur City Junction'),
  3, 60, 55, '08:20:00', '08:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Gangapur City Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Dausa Junction'),
  4, 80, 70, '09:20:00', '09:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Dausa Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  5, 55, 50, '10:35:00', NULL
);

-- ----------------------------------------------------------------
-- Sawai Madhopur Jaipur Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Sawai Madhopur Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Sawai Madhopur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Gangapur City Junction'),
  1, 60, 55, NULL, '07:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sawai Madhopur Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Gangapur City Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Dausa Junction'),
  2, 80, 70, '07:55:00', '08:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sawai Madhopur Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Dausa Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  3, 55, 50, '09:10:00', NULL
);

-- ----------------------------------------------------------------
-- Sawai Madhopur Kota Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Sawai Madhopur Kota Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Sawai Madhopur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bundi Junction'),
  1, 80, 70, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sawai Madhopur Kota Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Bundi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  2, 40, 35, '07:10:00', NULL
);