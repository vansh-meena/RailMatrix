-- DEHLI CORRIDORS
-- ================================================================
-- ROUTES DATA
-- Format: (train_id, departure_station_id, destination_station_id,
--           stop_number, distance_km, travel_time_minutes,
--           arrival_time, departure_time)
-- Using subqueries to avoid hardcoded IDs
-- ================================================================

-- ----------------------------------------------------------------
-- TRAIN: Rajdhani Express (New Delhi -> Mumbai Central)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  1, 458, 330, NULL, '16:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  2, 492, 360, '22:35:00', '22:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  3, 130, 90, '03:10:00', '03:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  4, 263, 180, '08:35:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: August Kranti Rajdhani (New Delhi -> Mumbai Central)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'August Kranti Rajdhani'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  1, 950, 630, NULL, '17:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'August Kranti Rajdhani'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  2, 130, 90, '04:25:00', '04:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'August Kranti Rajdhani'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  3, 263, 180, '09:45:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Mumbai Mail (New Delhi -> CSMT)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Mumbai Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Agra Cantt'),
  1, 195, 130, NULL, '20:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mumbai Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Agra Cantt'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  2, 263, 180, '22:15:00', '22:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mumbai Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  3, 492, 360, '04:10:00', '04:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mumbai Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  4, 130, 90, '10:30:00', '10:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mumbai Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Chhatrapati Shivaji Maharaj Terminus'),
  5, 263, 190, '14:10:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Rajdhani Kolkata (New Delhi -> Howrah)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Kolkata'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  1, 440, 280, NULL, '17:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Kolkata'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  2, 190, 120, '21:40:00', '21:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Kolkata'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  3, 250, 180, '00:05:00', '00:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Kolkata'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  4, 530, 330, '04:25:00', '04:30:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Poorva Express (New Delhi -> Howrah)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Poorva Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  1, 440, 300, NULL, '10:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Poorva Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  2, 190, 140, '15:05:00', '15:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Poorva Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Varanasi Junction'),
  3, 120, 90, '17:30:00', '17:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Poorva Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Varanasi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  4, 250, 180, '20:50:00', '21:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Poorva Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  5, 530, 360, '02:30:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Tamil Nadu Express (New Delhi -> Chennai Central)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Tamil Nadu Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  1, 702, 480, NULL, '22:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Tamil Nadu Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  2, 352, 270, '06:30:00', '06:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Tamil Nadu Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  3, 497, 390, '13:10:00', '13:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Tamil Nadu Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  4, 664, 480, '19:30:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Bhopal Shatabdi (New Delhi -> Bhopal)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Bhopal Shatabdi'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Agra Cantt'),
  1, 195, 120, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bhopal Shatabdi'),
  (SELECT station_id FROM stations WHERE station_name = 'Agra Cantt'),
  (SELECT station_id FROM stations WHERE station_name = 'Gwalior Junction'),
  2, 119, 80, '08:00:00', '08:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bhopal Shatabdi'),
  (SELECT station_id FROM stations WHERE station_name = 'Gwalior Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  3, 388, 255, '09:25:00', '09:30:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Pink City Express (New Delhi -> Jaipur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Pink City Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  1, 304, 270, NULL, '06:05:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Ajmer Shatabdi (New Delhi -> Ajmer)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Shatabdi'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  1, 304, 210, NULL, '06:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ajmer Shatabdi'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  2, 135, 100, '09:35:00', '09:40:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Lucknow Mail (New Delhi -> Lucknow)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Lucknow Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  1, 440, 300, NULL, '22:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Lucknow Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Lucknow Charbagh'),
  2, 75, 60, '03:10:00', '03:15:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Patna Rajdhani (New Delhi -> Patna)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Patna Rajdhani'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  1, 440, 280, NULL, '18:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Patna Rajdhani'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  2, 190, 120, '23:05:00', '23:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Patna Rajdhani'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  3, 250, 175, '01:30:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Vande Bharat Express (New Delhi -> Varanasi)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Vande Bharat Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  1, 440, 240, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Vande Bharat Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  2, 190, 100, '10:00:00', '10:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Vande Bharat Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Varanasi Junction'),
  3, 120, 75, '11:45:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Gatimaan Express (Hazrat Nizamuddin -> Agra Cantt)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Gatimaan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Hazrat Nizamuddin'),
  (SELECT station_id FROM stations WHERE station_name = 'Mathura Junction'),
  1, 147, 80, NULL, '08:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Gatimaan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Mathura Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Agra Cantt'),
  2, 58, 40, '09:30:00', '09:35:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Kerala Express (New Delhi -> Thiruvananthapuram)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Kerala Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  1, 1092, 720, NULL, '11:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kerala Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  2, 497, 390, '23:35:00', '23:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kerala Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  3, 574, 420, '06:25:00', '06:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kerala Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  4, 517, 390, '13:15:00', '13:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Kerala Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  5, 220, 180, '18:30:00', NULL
);