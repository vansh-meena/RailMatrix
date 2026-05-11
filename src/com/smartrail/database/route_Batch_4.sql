-- CROSS COUNTRY AND REMAINING
-- ----------------------------------------------------------------
-- TRAIN: Vivek Express (Dibrugarh -> Thiruvananthapuram)
-- India's longest train route
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Vivek Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Dibrugarh Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Guwahati Junction'),
  1, 480, 360, NULL, '21:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Vivek Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Guwahati Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  2, 1031, 660, '09:30:00', '09:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Vivek Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhubaneswar Junction'),
  3, 438, 360, '20:45:00', '20:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Vivek Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhubaneswar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Visakhapatnam Junction'),
  4, 440, 360, '02:55:00', '03:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Vivek Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Visakhapatnam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  5, 347, 270, '09:05:00', '09:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Vivek Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  6, 432, 360, '14:15:00', '14:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Vivek Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Coimbatore Junction'),
  7, 497, 390, '20:25:00', '20:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Vivek Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Coimbatore Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  8, 190, 180, '03:05:00', '03:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Vivek Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  9, 220, 180, '06:15:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Himsagar Express (Jammu Tawi -> Thiruvananthapuram)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Himsagar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jammu Tawi'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  1, 585, 420, NULL, '23:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Himsagar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  2, 1092, 720, '06:55:00', '07:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Himsagar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  3, 497, 390, '19:10:00', '19:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Himsagar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  4, 574, 420, '02:25:00', '02:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Himsagar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  5, 517, 390, '09:05:00', '09:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Himsagar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  6, 220, 180, '15:15:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Garib Rath (Hazrat Nizamuddin -> Bangalore)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Garib Rath'),
  (SELECT station_id FROM stations WHERE station_name = 'Hazrat Nizamuddin'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  1, 1092, 720, NULL, '15:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Garib Rath'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  2, 497, 390, '03:45:00', '03:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Garib Rath'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  3, 574, 420, '10:25:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Humsafar Express (New Delhi -> Howrah)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Humsafar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  1, 440, 280, NULL, '11:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Humsafar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  2, 190, 130, '16:05:00', '16:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Humsafar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  3, 250, 175, '18:20:00', '18:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Humsafar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  4, 530, 360, '00:30:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Sampark Kranti (Amritsar -> Secunderabad)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Sampark Kranti'),
  (SELECT station_id FROM stations WHERE station_name = 'Amritsar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  1, 449, 330, NULL, '17:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sampark Kranti'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  2, 702, 480, '23:35:00', '23:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sampark Kranti'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  3, 352, 270, '07:45:00', '07:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Sampark Kranti'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  4, 497, 390, '14:05:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Tejas Express (New Delhi -> Lucknow)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Tejas Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  1, 440, 270, NULL, '06:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Tejas Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Lucknow Charbagh'),
  2, 75, 50, '10:40:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Nagpur Express (New Delhi -> Nagpur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Nagpur Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  1, 702, 480, NULL, '20:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Nagpur Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  2, 352, 270, '04:45:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Vidarbha Express (Nagpur -> Mumbai)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Vidarbha Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  1, 572, 450, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Vidarbha Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  2, 192, 180, '13:30:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Gondia Express (Howrah -> Nagpur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Gondia Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhubaneswar Junction'),
  1, 438, 360, NULL, '23:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Gondia Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhubaneswar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Raipur Junction'),
  2, 440, 360, '05:55:00', '06:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Gondia Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Raipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  3, 295, 240, '12:05:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Raipur Express (New Delhi -> Raipur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Raipur Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  1, 702, 480, NULL, '22:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Raipur Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  2, 352, 270, '06:15:00', '06:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Raipur Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Raipur Junction'),
  3, 295, 240, '11:25:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Navyug Express (New Delhi -> Mangaluru)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Navyug Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  1, 1092, 720, NULL, '08:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Navyug Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  2, 497, 390, '20:20:00', '20:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Navyug Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  3, 574, 420, '03:10:00', '03:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Navyug Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mangaluru Central'),
  4, 352, 300, '10:20:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Antyodaya Express (LTT -> Howrah)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Antyodaya Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Lokmanya Tilak Terminus'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  1, 837, 600, NULL, '11:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Antyodaya Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Raipur Junction'),
  2, 295, 240, '21:00:00', '21:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Antyodaya Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Raipur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  3, 1000, 660, '01:10:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Lakshadweep Express (LTT -> Thiruvananthapuram)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Lakshadweep Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Lokmanya Tilak Terminus'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  1, 192, 180, NULL, '09:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Lakshadweep Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Pune Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  2, 840, 600, '12:10:00', '12:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Lakshadweep Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bangalore City Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  3, 517, 390, '22:20:00', '22:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Lakshadweep Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ernakulam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Thiruvananthapuram Central'),
  4, 220, 180, '04:30:00', NULL
);