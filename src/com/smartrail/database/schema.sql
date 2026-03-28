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
    departure VARCHAR(100),
    destination VARCHAR(100),
    total_seats INT NOT NULL,
    available_seats INT
);

-- passengers table
CREATE TABLE passengers (
    passenger_id INT PRIMARY KEY AUTO_INCREMENT,
    passenger_name VARCHAR(100),
    age INT,
    gender VARCHAR(10)
);

-- bookings table
CREATE TABLE bookings (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT,
    passenger_id INT,
    seats_booked INT,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (train_id) REFERENCES trains(train_id),
    FOREIGN KEY (passenger_id) REFERENCES passengers(passenger_id)
);

-- routes table
CREATE TABLE routes (
    route_id INT PRIMARY KEY AUTO_INCREMENT,
    departure_station_id INT NOT NULL,
    destination_station_id INT NOT NULL,
    distance_km INT NOT NULL,
    travel_time_minutes INT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,

    FOREIGN KEY (departure_station_id) REFERENCES stations(station_id),
    FOREIGN KEY (destination_station_id) REFERENCES stations(station_id)
);