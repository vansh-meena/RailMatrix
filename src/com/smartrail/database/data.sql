-- stations data
INSERT INTO stations (station_name, city) VALUES
('Jaipur Junction', 'Jaipur'),
('Delhi Junction', 'Delhi'),
('Kota Junction', 'Kota'),
('Agra Junction', 'Agra'),
('Mumbai Junction', 'Mumbai');

-- routes data (AUTO-SAFE)
INSERT INTO routes (departure_station_id, destination_station_id, distance_km, travel_time_minutes)
VALUES
(
 (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
 (SELECT station_id FROM stations WHERE station_name = 'Delhi Junction'),
 300, 240
),
(
 (SELECT station_id FROM stations WHERE station_name = 'Delhi Junction'),
 (SELECT station_id FROM stations WHERE station_name = 'Agra Junction'),
 200, 180
),
(
 (SELECT station_id FROM stations WHERE station_name = 'Jaipur Junction'),
 (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
 250, 200
),
(
 (SELECT station_id FROM stations WHERE station_name = 'Kota Junction'),
 (SELECT station_id FROM stations WHERE station_name = 'Mumbai Junction'),
 900, 720
);