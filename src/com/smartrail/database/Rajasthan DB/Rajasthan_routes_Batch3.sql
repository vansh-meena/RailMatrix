-- ================================================================
-- RAJASTHAN ROUTES — BATCH 3
-- ================================================================

-- ----------------------------------------------------------------
-- Udaipur Delhi Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  (SELECT station_id FROM stations WHERE station_name = 'Chittorgarh Junction'),
  1, 115, 95, NULL, '19:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chittorgarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhilwara Junction'),
  2, 75, 65, '20:35:00', '20:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhilwara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  3, 140, 115, '21:45:00', '21:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  4, 135, 100, '23:50:00', '00:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  5, 170, 140, '01:40:00', '01:50:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  6, 160, 130, '04:10:00', NULL
);

-- ----------------------------------------------------------------
-- Udaipur Mumbai Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  (SELECT station_id FROM stations WHERE station_name = 'Himmatnagar Junction'),
  1, 180, 150, NULL, '15:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Himmatnagar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  2, 110, 90, '17:30:00', '17:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  3, 265, 200, '19:10:00', '19:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  4, 263, 180, '22:40:00', NULL
);

-- ----------------------------------------------------------------
-- Udaipur Ahmedabad Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Ahmedabad Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  1, 185, 150, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Ahmedabad Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  2, 220, 180, '08:30:00', NULL
);

-- ----------------------------------------------------------------
-- Udaipur Jaipur Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  (SELECT station_id FROM stations WHERE station_name = 'Chittorgarh Junction'),
  1, 115, 95, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Chittorgarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhilwara Junction'),
  2, 75, 65, '07:35:00', '07:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhilwara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  3, 140, 115, '08:45:00', '08:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  4, 135, 100, '10:50:00', NULL
);

-- ----------------------------------------------------------------
-- Udaipur Chittorgarh Passenger
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Udaipur Chittorgarh Passenger'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  (SELECT station_id FROM stations WHERE station_name = 'Chittorgarh Junction'),
  1, 115, 120, NULL, '07:00:00'
);

-- ----------------------------------------------------------------
-- Ajmer Delhi Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kishangarh Junction'),
  1, 45, 40, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kishangarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  2, 90, 75, '06:40:00', '06:50:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Dausa Junction'),
  3, 55, 50, '08:05:00', '08:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Dausa Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bandikui Junction'),
  4, 35, 30, '09:00:00', '09:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bandikui Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  5, 90, 75, '09:35:00', '09:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  6, 160, 130, '10:55:00', NULL
);

-- ----------------------------------------------------------------
-- Ajmer Mumbai Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nasirabad Junction'),
  1, 30, 30, NULL, '16:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Nasirabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Beawar Junction'),
  2, 25, 25, '16:30:00', '16:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Beawar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  3, 120, 100, '17:00:00', '17:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Falna Junction'),
  4, 80, 70, '18:45:00', '18:50:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Falna Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  5, 100, 85, '20:00:00', '20:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  6, 220, 180, '21:30:00', '21:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  7, 265, 200, '00:20:00', '00:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  8, 263, 180, '03:50:00', NULL
);

-- ----------------------------------------------------------------
-- Ajmer Jodhpur Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Beawar Junction'),
  1, 55, 50, NULL, '07:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Beawar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  2, 120, 100, '07:50:00', '07:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  3, 80, 70, '09:35:00', NULL
);

-- ----------------------------------------------------------------
-- Kota Delhi Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Sawai Madhopur Junction'),
  1, 110, 90, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Sawai Madhopur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Gangapur City Junction'),
  2, 60, 55, '07:30:00', '07:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Gangapur City Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bandikui Junction'),
  3, 100, 85, '08:30:00', '08:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bandikui Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  4, 90, 75, '10:00:00', '10:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  5, 170, 140, '11:25:00', '11:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  6, 160, 130, '13:55:00', NULL
);

-- ----------------------------------------------------------------
-- Kota Mumbai Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Chittorgarh Junction'),
  1, 150, 120, NULL, '18:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chittorgarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  2, 115, 95, '20:00:00', '20:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  3, 185, 150, '21:45:00', '21:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  4, 220, 180, '00:05:00', '00:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  5, 265, 200, '03:15:00', '03:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Mumbai Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  6, 263, 180, '06:45:00', NULL
);

-- ----------------------------------------------------------------
-- Kota Bhopal Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Bhopal Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Baran Junction'),
  1, 80, 70, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Bhopal Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Baran Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jhalawar Road Junction'),
  2, 50, 45, '07:10:00', '07:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kota Bhopal Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Jhalawar Road Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  3, 270, 220, '08:00:00', NULL
);

-- ----------------------------------------------------------------
-- Barmer Jodhpur Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Barmer Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Barmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Balotra Junction'),
  1, 100, 85, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Barmer Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Balotra Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Luni Junction'),
  2, 120, 100, '07:25:00', '07:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Barmer Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Luni Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  3, 30, 30, '09:10:00', NULL
);

-- ----------------------------------------------------------------
-- Barmer Delhi Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Barmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Barmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Balotra Junction'),
  1, 100, 85, NULL, '17:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Barmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Balotra Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  2, 150, 125, '18:25:00', '18:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Barmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  3, 80, 70, '20:40:00', '20:50:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Barmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  4, 200, 160, '22:00:00', '22:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Barmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  5, 135, 100, '01:10:00', '01:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Barmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  6, 304, 240, '03:00:00', NULL
);

-- ----------------------------------------------------------------
-- Jaisalmer Delhi Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaisalmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaisalmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Pokaran Junction'),
  1, 110, 90, NULL, '17:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaisalmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Pokaran Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  2, 180, 150, '18:30:00', '18:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaisalmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  3, 80, 70, '21:10:00', '21:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaisalmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  4, 200, 160, '22:30:00', '22:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaisalmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  5, 135, 100, '01:20:00', '01:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaisalmer Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  6, 304, 240, '03:10:00', NULL
);

-- ----------------------------------------------------------------
-- Jaisalmer Jodhpur Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaisalmer Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaisalmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Pokaran Junction'),
  1, 110, 90, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaisalmer Jodhpur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Pokaran Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  2, 180, 150, '07:30:00', NULL
);

-- ----------------------------------------------------------------
-- Abu Road Delhi Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Abu Road Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Falna Junction'),
  1, 100, 85, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Abu Road Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Falna Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  2, 80, 70, '07:25:00', '07:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Abu Road Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Marwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  3, 200, 160, '08:40:00', '08:50:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Abu Road Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  4, 135, 100, '11:30:00', '11:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Abu Road Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  5, 170, 140, '13:20:00', '13:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Abu Road Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  6, 160, 130, '15:50:00', NULL
);

-- ----------------------------------------------------------------
-- Abu Road Ahmedabad Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Abu Road Ahmedabad Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Abu Road Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  1, 220, 180, NULL, '07:00:00'
);

-- ----------------------------------------------------------------
-- Bhilwara Jaipur Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Bhilwara Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhilwara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kishangarh Junction'),
  1, 100, 85, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bhilwara Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Kishangarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Phulera Junction'),
  2, 60, 50, '07:25:00', '07:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bhilwara Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Phulera Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  3, 45, 40, '08:20:00', NULL
);

-- ----------------------------------------------------------------
-- Bhilwara Ajmer Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Bhilwara Ajmer Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhilwara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nasirabad Junction'),
  1, 110, 90, NULL, '07:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bhilwara Ajmer Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Nasirabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  2, 30, 30, '08:30:00', NULL
);

-- ----------------------------------------------------------------
-- Alwar Delhi Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Alwar Delhi Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Alwar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  1, 160, 130, NULL, '07:00:00'
);

-- ----------------------------------------------------------------
-- Bharatpur Agra Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Bharatpur Agra Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Bharatpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Agra Cantt'),
  1, 55, 50, NULL, '07:00:00'
);

-- ----------------------------------------------------------------
-- Bharatpur Delhi Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Bharatpur Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bharatpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mathura Junction'),
  1, 40, 35, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bharatpur Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Mathura Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  2, 147, 120, '06:35:00', NULL
);

-- ----------------------------------------------------------------
-- Chittorgarh Delhi Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Chittorgarh Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chittorgarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhilwara Junction'),
  1, 75, 65, NULL, '18:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Chittorgarh Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhilwara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  2, 140, 115, '19:05:00', '19:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Chittorgarh Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  3, 135, 100, '21:10:00', '21:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Chittorgarh Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  4, 304, 240, '23:00:00', NULL
);

-- ----------------------------------------------------------------
-- Chittorgarh Kota Intercity
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Chittorgarh Kota Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Chittorgarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nimbahera Junction'),
  1, 60, 55, NULL, '07:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Chittorgarh Kota Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Nimbahera Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  2, 90, 80, '07:55:00', NULL
);

-- ----------------------------------------------------------------
-- Sri Ganganagar Delhi Express
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Sri Ganganagar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Hanumangarh Junction'),
  1, 70, 60, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Hanumangarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Sadulpur Junction'),
  2, 120, 100, '07:00:00', '07:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Sadulpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Churu Junction'),
  3, 60, 55, '08:45:00', '08:50:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Churu Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Sikar Junction'),
  4, 100, 85, '09:45:00', '09:50:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Sikar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  5, 110, 90, '11:15:00', '11:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sri Ganganagar Delhi Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  6, 304, 240, '12:55:00', NULL
);