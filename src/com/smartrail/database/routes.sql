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

-- SOUTH INDIA, RAJASTHAN, GUJARAT
-- ----------------------------------------------------------------
-- TRAIN: Chennai Bangalore Shatabdi (Chennai -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Chennai Bangalore Shatabdi'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  1, 362, 300, NULL, '06:00:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Brindavan Express (Chennai -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Brindavan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  1, 362, 330, NULL, '07:45:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Island Express (Bangalore -> Thiruvananthapuram)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Island Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Coimbatore Junction'),
  1, 365, 300, NULL, '21:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Island Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Coimbatore Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  2, 190, 180, '02:00:00', '02:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Island Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  3, 220, 180, '05:30:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Rajdhani Bangalore (Hazrat Nizamuddin -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Bangalore'),
  (SELECT station_id FROM stations WHERE station_name = 'Hazrat Nizamuddin'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  1, 702, 480, NULL, '20:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Bangalore'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  2, 352, 270, '04:15:00', '04:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Bangalore'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  3, 497, 390, '11:05:00', '11:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Bangalore'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  4, 574, 420, '18:15:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Charminar Express (Chennai -> Hyderabad)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Charminar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  1, 432, 360, NULL, '18:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Charminar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Hyderabad Deccan'),
  2, 275, 210, '01:45:00', '01:55:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Godavari Express (Secunderabad -> Visakhapatnam)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Godavari Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  1, 275, 210, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Godavari Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Visakhapatnam Junction'),
  2, 347, 270, '09:30:00', '09:40:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Sabari Express (Ernakulam -> Thiruvananthapuram)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Sabari Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  1, 220, 180, NULL, '07:15:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Parasuram Express (Mangaluru -> Thiruvananthapuram)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Parasuram Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Mangaluru Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Kozhikode Junction'),
  1, 190, 150, NULL, '06:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Parasuram Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kozhikode Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  2, 190, 180, '09:15:00', '09:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Parasuram Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  3, 220, 180, '12:25:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Intercity Express (Ernakulam -> Kozhikode)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Intercity Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kozhikode Junction'),
  1, 190, 180, NULL, '06:30:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Mewar Express (New Delhi -> Udaipur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Mewar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  1, 304, 270, NULL, '19:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mewar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  2, 135, 100, '00:10:00', '00:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mewar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  3, 285, 210, '02:05:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Mandore Express (New Delhi -> Jodhpur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Mandore Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  1, 304, 270, NULL, '22:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mandore Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  2, 330, 270, '02:30:00', '02:40:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Chetak Express (Ajmer -> Udaipur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Chetak Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  1, 285, 210, NULL, '06:10:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Jaipur Intercity (Jaipur -> Kota)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  1, 254, 210, NULL, '06:05:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Shatabdi Ahmedabad (Mumbai -> Ahmedabad)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Shatabdi Ahmedabad'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  1, 263, 180, NULL, '06:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Shatabdi Ahmedabad'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  2, 130, 90, '09:25:00', '09:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Shatabdi Ahmedabad'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  3, 100, 75, '11:00:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Gujarat Mail (CSMT -> Ahmedabad)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Gujarat Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Chhatrapati Shivaji Maharaj Terminus'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  1, 263, 210, NULL, '21:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Gujarat Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  2, 130, 100, '01:30:00', '01:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Gujarat Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  3, 100, 80, '03:05:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Double Decker Express (Ahmedabad -> Surat)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Double Decker Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  1, 100, 80, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Double Decker Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  2, 130, 100, '07:20:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Udyan Express (CSMT -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Udyan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chhatrapati Shivaji Maharaj Terminus'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  1, 192, 180, NULL, '08:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udyan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  2, 579, 480, '11:05:00', '11:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udyan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  3, 574, 420, '19:35:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Konkan Kanya Express (CSMT -> Mangaluru)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Konkan Kanya Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chhatrapati Shivaji Maharaj Terminus'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  1, 192, 180, NULL, '22:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Konkan Kanya Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mangaluru Central'),
  2, 580, 510, '01:00:00', '01:10:00'
);

-- SOUTH INDIA, RAJASTHAN, GUJARAT
-- ----------------------------------------------------------------
-- TRAIN: Chennai Bangalore Shatabdi (Chennai -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Chennai Bangalore Shatabdi'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  1, 362, 300, NULL, '06:00:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Brindavan Express (Chennai -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Brindavan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  1, 362, 330, NULL, '07:45:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Island Express (Bangalore -> Thiruvananthapuram)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Island Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Coimbatore Junction'),
  1, 365, 300, NULL, '21:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Island Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Coimbatore Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  2, 190, 180, '02:00:00', '02:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Island Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  3, 220, 180, '05:30:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Rajdhani Bangalore (Hazrat Nizamuddin -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Bangalore'),
  (SELECT station_id FROM stations WHERE station_name = 'Hazrat Nizamuddin'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  1, 702, 480, NULL, '20:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Bangalore'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  2, 352, 270, '04:15:00', '04:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Bangalore'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  3, 497, 390, '11:05:00', '11:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Bangalore'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  4, 574, 420, '18:15:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Charminar Express (Chennai -> Hyderabad)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Charminar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  1, 432, 360, NULL, '18:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Charminar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Hyderabad Deccan'),
  2, 275, 210, '01:45:00', '01:55:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Godavari Express (Secunderabad -> Visakhapatnam)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Godavari Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  1, 275, 210, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Godavari Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Visakhapatnam Junction'),
  2, 347, 270, '09:30:00', '09:40:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Sabari Express (Ernakulam -> Thiruvananthapuram)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Sabari Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  1, 220, 180, NULL, '07:15:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Parasuram Express (Mangaluru -> Thiruvananthapuram)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Parasuram Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Mangaluru Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Kozhikode Junction'),
  1, 190, 150, NULL, '06:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Parasuram Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kozhikode Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  2, 190, 180, '09:15:00', '09:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Parasuram Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  3, 220, 180, '12:25:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Intercity Express (Ernakulam -> Kozhikode)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Intercity Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kozhikode Junction'),
  1, 190, 180, NULL, '06:30:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Mewar Express (New Delhi -> Udaipur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Mewar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  1, 304, 270, NULL, '19:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mewar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  2, 135, 100, '00:10:00', '00:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mewar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  3, 285, 210, '02:05:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Mandore Express (New Delhi -> Jodhpur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Mandore Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  1, 304, 270, NULL, '22:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mandore Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  2, 330, 270, '02:30:00', '02:40:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Chetak Express (Ajmer -> Udaipur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Chetak Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  1, 285, 210, NULL, '06:10:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Jaipur Intercity (Jaipur -> Kota)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  1, 254, 210, NULL, '06:05:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Shatabdi Ahmedabad (Mumbai -> Ahmedabad)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Shatabdi Ahmedabad'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  1, 263, 180, NULL, '06:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Shatabdi Ahmedabad'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  2, 130, 90, '09:25:00', '09:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Shatabdi Ahmedabad'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  3, 100, 75, '11:00:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Gujarat Mail (CSMT -> Ahmedabad)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Gujarat Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Chhatrapati Shivaji Maharaj Terminus'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  1, 263, 210, NULL, '21:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Gujarat Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  2, 130, 100, '01:30:00', '01:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Gujarat Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  3, 100, 80, '03:05:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Double Decker Express (Ahmedabad -> Surat)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Double Decker Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  1, 100, 80, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Double Decker Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  2, 130, 100, '07:20:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Udyan Express (CSMT -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Udyan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chhatrapati Shivaji Maharaj Terminus'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  1, 192, 180, NULL, '08:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udyan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  2, 579, 480, '11:05:00', '11:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udyan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  3, 574, 420, '19:35:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Konkan Kanya Express (CSMT -> Mangaluru)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Konkan Kanya Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chhatrapati Shivaji Maharaj Terminus'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  1, 192, 180, NULL, '22:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Konkan Kanya Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mangaluru Central'),
  2, 580, 510, '01:00:00', '01:10:00'
);

-- SOUTH INDIA, RAJASTHAN, GUJARAT
-- ----------------------------------------------------------------
-- TRAIN: Chennai Bangalore Shatabdi (Chennai -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Chennai Bangalore Shatabdi'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  1, 362, 300, NULL, '06:00:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Brindavan Express (Chennai -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Brindavan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  1, 362, 330, NULL, '07:45:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Island Express (Bangalore -> Thiruvananthapuram)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Island Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Coimbatore Junction'),
  1, 365, 300, NULL, '21:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Island Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Coimbatore Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  2, 190, 180, '02:00:00', '02:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Island Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  3, 220, 180, '05:30:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Rajdhani Bangalore (Hazrat Nizamuddin -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Bangalore'),
  (SELECT station_id FROM stations WHERE station_name = 'Hazrat Nizamuddin'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  1, 702, 480, NULL, '20:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Bangalore'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  2, 352, 270, '04:15:00', '04:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Bangalore'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  3, 497, 390, '11:05:00', '11:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Bangalore'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  4, 574, 420, '18:15:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Charminar Express (Chennai -> Hyderabad)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Charminar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  1, 432, 360, NULL, '18:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Charminar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Hyderabad Deccan'),
  2, 275, 210, '01:45:00', '01:55:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Godavari Express (Secunderabad -> Visakhapatnam)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Godavari Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  1, 275, 210, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Godavari Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Visakhapatnam Junction'),
  2, 347, 270, '09:30:00', '09:40:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Sabari Express (Ernakulam -> Thiruvananthapuram)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Sabari Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  1, 220, 180, NULL, '07:15:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Parasuram Express (Mangaluru -> Thiruvananthapuram)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Parasuram Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Mangaluru Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Kozhikode Junction'),
  1, 190, 150, NULL, '06:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Parasuram Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kozhikode Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  2, 190, 180, '09:15:00', '09:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Parasuram Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  3, 220, 180, '12:25:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Intercity Express (Ernakulam -> Kozhikode)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Intercity Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kozhikode Junction'),
  1, 190, 180, NULL, '06:30:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Mewar Express (New Delhi -> Udaipur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Mewar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  1, 304, 270, NULL, '19:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mewar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  2, 135, 100, '00:10:00', '00:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mewar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  3, 285, 210, '02:05:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Mandore Express (New Delhi -> Jodhpur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Mandore Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  1, 304, 270, NULL, '22:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Mandore Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jodhpur Junction'),
  2, 330, 270, '02:30:00', '02:40:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Chetak Express (Ajmer -> Udaipur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Chetak Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ajmer Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Udaipur City'),
  1, 285, 210, NULL, '06:10:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Jaipur Intercity (Jaipur -> Kota)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Jaipur Intercity'),
  (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
  1, 254, 210, NULL, '06:05:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Shatabdi Ahmedabad (Mumbai -> Ahmedabad)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Shatabdi Ahmedabad'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  1, 263, 180, NULL, '06:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Shatabdi Ahmedabad'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  2, 130, 90, '09:25:00', '09:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Shatabdi Ahmedabad'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  3, 100, 75, '11:00:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Gujarat Mail (CSMT -> Ahmedabad)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Gujarat Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Chhatrapati Shivaji Maharaj Terminus'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  1, 263, 210, NULL, '21:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Gujarat Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  2, 130, 100, '01:30:00', '01:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Gujarat Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  3, 100, 80, '03:05:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Double Decker Express (Ahmedabad -> Surat)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Double Decker Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ahmedabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  1, 100, 80, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Double Decker Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  2, 130, 100, '07:20:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Udyan Express (CSMT -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Udyan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chhatrapati Shivaji Maharaj Terminus'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  1, 192, 180, NULL, '08:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udyan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  2, 579, 480, '11:05:00', '11:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Udyan Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  3, 574, 420, '19:35:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Konkan Kanya Express (CSMT -> Mangaluru)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Konkan Kanya Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chhatrapati Shivaji Maharaj Terminus'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  1, 192, 180, NULL, '22:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Konkan Kanya Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mangaluru Central'),
  2, 580, 510, '01:00:00', '01:10:00'
);

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