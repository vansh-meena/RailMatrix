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