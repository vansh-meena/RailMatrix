-- DROP ORDER (foreign keys first)
DROP TABLE IF EXISTS passengers;
DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS routes;
DROP TABLE IF EXISTS train_schedule;
DROP TABLE IF EXISTS trains;
DROP TABLE IF EXISTS stations;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS admins;

-- users table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);

-- admins table
CREATE TABLE admins (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);
-- (email, password) -> (admin001@gmail.com, admin001)

-- stations table
CREATE TABLE stations (
    station_id INT PRIMARY KEY AUTO_INCREMENT,
    station_name VARCHAR(100) NOT NULL UNIQUE,
    city VARCHAR(100)
);

-- trains table
CREATE TABLE trains (
    train_id INT PRIMARY KEY AUTO_INCREMENT,
    train_name VARCHAR(100) NOT NULL,
    train_type VARCHAR(50),
    departure VARCHAR(100),
    destination VARCHAR(100),
    total_seats INT NOT NULL,
    base_fare DECIMAL(10,2) DEFAULT 0.00,
    fare_per_km DECIMAL(5,2) DEFAULT 0.50
);

-- bookings table
CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    train_id INT,
    journey_date DATE,
    total_passengers INT,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'ACTIVE';
    cancelled_at TIMESTAMP NULL;
    refund_amount DECIMAL(10,2) DEFAULT 0.00;

    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (train_id) REFERENCES trains(train_id)
);

-- passengers table
CREATE TABLE passengers (
    passenger_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT,
    passenger_name VARCHAR(100),
    age INT,
    gender VARCHAR(10),
    seat_number INT,

    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);

-- routes table
CREATE TABLE routes (
    route_id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT,
    departure_station_id INT,
    destination_station_id INT,
    stop_number INT,
    distance_km INT,
    travel_time_minutes INT,
    arrival_time TIME,
    departure_time TIME,
    is_active TINYINT DEFAULT 1,

    FOREIGN KEY (train_id) REFERENCES trains(train_id),
    FOREIGN KEY (departure_station_id) REFERENCES stations(station_id),
    FOREIGN KEY (destination_station_id) REFERENCES stations(station_id)
);

-- train schedule table
CREATE TABLE train_schedule (
    schedule_id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT,
    journey_date DATE,
    available_seats INT,

    FOREIGN KEY (train_id) REFERENCES trains(train_id)
);