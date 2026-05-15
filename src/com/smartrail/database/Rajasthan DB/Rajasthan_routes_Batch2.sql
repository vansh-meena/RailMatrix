-- ================================================================
-- RAJASTHAN ROUTES — BATCH 2 (Jodhpur, Bikaner Based)
-- ================================================================

-- ----------------------------------------------------------------
-- Jodhpur Delhi Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Delhi Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  1, 80, 70, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Delhi Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Beawar Junction'),
  2, 120, 100, '07:10:00', '07:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Delhi Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Beawar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  3, 55, 50, '08:55:00', '09:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Delhi Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  4, 135, 100, '09:50:00', '10:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Delhi Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  5, 170, 140, '11:40:00', '11:50:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Delhi Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  6, 160, 130, '14:00:00', NULL
);

-- ----------------------------------------------------------------
-- Jodhpur Mumbai Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Luni Junction'),
  1, 30, 30, NULL, '18:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Luni Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  2, 60, 55, '18:30:00', '18:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Falna Junction'),
  3, 80, 70, '19:30:00', '19:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Falna Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  4, 100, 85, '20:45:00', '20:50:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  5, 220, 180, '22:15:00', '22:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  6, 265, 200, '01:45:00', '01:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  7, 263, 180, '05:15:00', NULL
);

-- ----------------------------------------------------------------
-- Jodhpur Jaisalmer Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Jaisalmer Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Luni Junction'),
  1, 30, 30, NULL, '23:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Jaisalmer Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Luni Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Pokaran Junction'),
  2, 180, 150, '23:30:00', '23:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Jaisalmer Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Pokaran Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaisalmer Junction'),
  3, 110, 90, '02:05:00', NULL
);

-- ----------------------------------------------------------------
-- Jodhpur Bikaner Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Bikaner Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagaur Junction'),
  1, 135, 110, NULL, '07:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Bikaner Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagaur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bikaner Junction'),
  2, 140, 115, '09:10:00', NULL
);

-- ----------------------------------------------------------------
-- Jodhpur Barmer Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Barmer Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Luni Junction'),
  1, 30, 30, NULL, '06:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Barmer Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Luni Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Balotra Junction'),
  2, 120, 100, '07:00:00', '07:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Barmer Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Balotra Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Barmer Junction'),
  3, 100, 85, '08:45:00', NULL
);

-- ----------------------------------------------------------------
-- Jodhpur Ahmedabad Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Ahmedabad Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  1, 80, 70, NULL, '19:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Ahmedabad Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Falna Junction'),
  2, 80, 70, '20:10:00', '20:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Ahmedabad Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Falna Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  3, 100, 85, '21:25:00', '21:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jodhpur Ahmedabad Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  4, 220, 180, '22:55:00', NULL
);

-- ----------------------------------------------------------------
-- Bikaner Delhi Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Bikaner Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bikaner Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ratangarh Junction'),
  1, 120, 100, NULL, '05:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bikaner Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ratangarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Churu Junction'),
  2, 60, 55, '07:25:00', '07:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bikaner Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Churu Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Sikar Junction'),
  3, 100, 85, '08:25:00', '08:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bikaner Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Sikar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ringas Junction'),
  4, 60, 55, '09:55:00', '10:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bikaner Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ringas Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  5, 55, 50, '10:55:00', '11:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bikaner Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  6, 170, 140, '11:55:00', '12:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bikaner Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  7, 160, 130, '14:25:00', NULL
);

-- ----------------------------------------------------------------
-- Bikaner Jodhpur Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Bikaner Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Bikaner Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagaur Junction'),
  1, 140, 115, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bikaner Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagaur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Merta Road Junction'),
  2, 60, 55, '07:55:00', '08:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bikaner Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Merta Road Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  3, 80, 70, '08:55:00', NULL
);

-- ----------------------------------------------------------------
-- Sri Ganganagar Bikaner Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Bikaner Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Sri Ganganagar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Suratgarh Junction'),
  1, 90, 75, NULL, '07:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Bikaner Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Suratgarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nokha Junction'),
  2, 100, 85, '08:15:00', '08:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Bikaner Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Nokha Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bikaner Junction'),
  3, 50, 45, '09:45:00', NULL
);

-- ----------------------------------------------------------------
-- Sri Ganganagar Jaipur Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Jaipur Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Sri Ganganagar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Suratgarh Junction'),
  1, 90, 75, NULL, '08:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Jaipur Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Suratgarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bikaner Junction'),
  2, 150, 120, '09:15:00', '09:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Jaipur Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bikaner Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ratangarh Junction'),
  3, 120, 100, '11:25:00', '11:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Jaipur Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ratangarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Sikar Junction'),
  4, 100, 85, '13:15:00', '13:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Jaipur Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Sikar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  5, 110, 90, '14:45:00', NULL
);