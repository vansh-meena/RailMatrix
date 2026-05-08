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
    total_seats INT NOT NULL
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

-- bookings table
CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    train_id INT,
    journey_date DATE,
    total_passengers INT,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (train_id) REFERENCES trains(train_id)
);

-- routes table
CREATE TABLE routes (
    route_id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT,
    station_id INT,
    stop_number INT,
    arrival_time TIME,
    departure_time TIME,

    FOREIGN KEY (train_id) REFERENCES trains(train_id),
    FOREIGN KEY (station_id) REFERENCES stations(station_id)
);

-- Users
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);

-- Train Schedule
CREATE TABLE train_schedule (
    schedule_id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT,
    journey_date DATE,
    available_seats INT,

    FOREIGN KEY (train_id) REFERENCES trains(train_id)
);

-- Admin
CREATE TABLE admins (
    admin_id INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100)
);
-- (email, password) -> (admin001@gmail.com, admin001)