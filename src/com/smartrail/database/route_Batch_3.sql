-- NORTHEAST, MP, UP, BIHAR, ODISHA
-- ----------------------------------------------------------------
-- TRAIN: Rajdhani Guwahati (New Delhi -> Guwahati)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Guwahati'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  1, 440, 280, NULL, '17:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Guwahati'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  2, 440, 300, '22:20:00', '22:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Guwahati'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  3, 530, 360, '04:00:00', '04:10:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Rajdhani Guwahati'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Guwahati Junction'),
  4, 1031, 660, '10:10:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Brahmaputra Mail (New Delhi -> Dibrugarh)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Brahmaputra Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  1, 440, 300, NULL, '11:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Brahmaputra Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Varanasi Junction'),
  2, 320, 240, '16:55:00', '17:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Brahmaputra Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Varanasi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  3, 250, 180, '21:05:00', '21:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Brahmaputra Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  4, 530, 360, '03:15:00', '03:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Brahmaputra Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Guwahati Junction'),
  5, 1031, 660, '09:25:00', '09:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Brahmaputra Mail'),
  (SELECT station_id FROM stations WHERE station_name = 'Guwahati Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Dibrugarh Junction'),
  6, 480, 360, '21:35:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: North East Express (Anand Vihar -> Guwahati)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'North East Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Anand Vihar Terminal'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  1, 1000, 660, NULL, '19:40:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'North East Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  2, 530, 360, '06:40:00', '06:50:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'North East Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Guwahati Junction'),
  3, 1031, 660, '12:50:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Kamrup Express (Howrah -> Guwahati)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Kamrup Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Guwahati Junction'),
  1, 1031, 660, NULL, '17:40:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Saraighat Express (Howrah -> Guwahati)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Saraighat Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Guwahati Junction'),
  1, 1031, 660, NULL, '15:55:00'
);

-- ----------------------------------------------------------------
-- TRAIN: Gondwana Express (Hazrat Nizamuddin -> Jabalpur)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Gondwana Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Hazrat Nizamuddin'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  1, 702, 480, NULL, '15:25:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Gondwana Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Jabalpur Junction'),
  2, 330, 240, '23:25:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Malwa Express (Indore -> New Delhi)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Malwa Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Indore Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ujjain Junction'),
  1, 55, 60, NULL, '11:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Malwa Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ujjain Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  2, 188, 150, '12:55:00', '13:05:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Malwa Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhopal Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  3, 702, 480, '15:35:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Avantika Express (Indore -> Mumbai)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Avantika Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Indore Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Ujjain Junction'),
  1, 55, 60, NULL, '19:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Avantika Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Ujjain Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  2, 400, 330, '20:45:00', '20:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Avantika Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vadodara Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  3, 130, 100, '02:25:00', '02:30:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Avantika Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Surat Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  4, 263, 180, '05:30:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Narmada Express (Jabalpur -> Mumbai)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Narmada Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Jabalpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  1, 330, 270, NULL, '07:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Narmada Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Nagpur Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Mumbai Central'),
  2, 837, 600, '11:30:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Magadh Express (New Delhi -> Patna)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Magadh Express'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  1, 440, 300, NULL, '07:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Magadh Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Kanpur Central'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  2, 190, 140, '12:15:00', '12:20:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Magadh Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Allahabad Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  3, 250, 180, '14:40:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Ganga Damodar Express (Patna -> Howrah)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Ganga Damodar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Patna Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Gaya Junction'),
  1, 100, 90, NULL, '06:00:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Ganga Damodar Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Gaya Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  2, 435, 330, '07:30:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Konark Express (Howrah -> Puri)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Konark Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhubaneswar Junction'),
  1, 438, 360, NULL, '06:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Konark Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhubaneswar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Puri Junction'),
  2, 60, 60, '13:05:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Bhubaneswar Rajdhani (New Delhi -> Bhubaneswar)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Bhubaneswar Rajdhani'),
  (SELECT station_id FROM stations WHERE station_name = 'New Delhi Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  1, 1450, 900, NULL, '20:15:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Bhubaneswar Rajdhani'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhubaneswar Junction'),
  2, 438, 360, '10:55:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Coromandel Express (Howrah -> Chennai)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Coromandel Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhubaneswar Junction'),
  1, 438, 360, NULL, '14:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Coromandel Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhubaneswar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Visakhapatnam Junction'),
  2, 440, 360, '20:45:00', '20:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Coromandel Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Visakhapatnam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  3, 347, 270, '03:25:00', '03:35:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Coromandel Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Vijayawada Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Chennai Central'),
  4, 432, 360, '08:35:00', NULL
);

-- ----------------------------------------------------------------
-- TRAIN: Falaknuma Express (Howrah -> Secunderabad)
-- ----------------------------------------------------------------
INSERT INTO routes (train_id, departure_station_id, destination_station_id, stop_number, distance_km, travel_time_minutes, arrival_time, departure_time)
VALUES
(
  (SELECT train_id FROM trains WHERE train_name = 'Falaknuma Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Howrah Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhubaneswar Junction'),
  1, 438, 360, NULL, '13:45:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Falaknuma Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Bhubaneswar Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Visakhapatnam Junction'),
  2, 440, 360, '19:45:00', '19:55:00'
),
(
  (SELECT train_id FROM trains WHERE train_name = 'Falaknuma Express'),
  (SELECT station_id FROM stations WHERE station_name = 'Visakhapatnam Junction'),
  (SELECT station_id FROM stations WHERE station_name = 'Secunderabad Junction'),
  3, 720, 540, '03:35:00', NULL
);