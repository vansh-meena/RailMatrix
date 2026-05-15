-- ================================================================
-- RAJASTHAN TRAINS
-- Excludes already inserted:
-- Pink City Express, Ajmer Shatabdi, Mandore Express,
-- Mewar Express, Chetak Express, Suryanagari Express,
-- Jaipur Intercity
-- ================================================================
INSERT INTO trains (train_name, train_type, departure, destination, total_seats, base_fare, fare_per_km) VALUES

-- JAIPUR BASED
('Jaipur Delhi Intercity', 'Intercity', 'Jaipur Junction', 'New Delhi Junction', 300, 120.00, 0.80),
('Jaipur Mumbai Express', 'Express', 'Jaipur Junction', 'Mumbai Central', 500, 280.00, 0.82),
('Jaipur Jodhpur Intercity', 'Intercity', 'Jaipur Junction', 'Jodhpur Junction', 200, 80.00, 0.70),
('Jaipur Bikaner Express', 'Express', 'Jaipur Junction', 'Bikaner Junction', 300, 120.00, 0.75),
('Jaipur Ahmedabad Express', 'Express', 'Jaipur Junction', 'Ahmedabad Junction', 400, 200.00, 0.80),
('Jaipur Lucknow Express', 'Express', 'Jaipur Junction', 'Lucknow Charbagh', 350, 220.00, 0.82),
('Jaipur Chennai Express', 'Express', 'Jaipur Junction', 'Chennai Central', 450, 380.00, 0.85),
('Jaipur Hyderabad Express', 'Express', 'Jaipur Junction', 'Secunderabad Junction', 400, 350.00, 0.85),
('Jaipur Kolkata Express', 'Express', 'Jaipur Junction', 'Howrah Junction', 450, 320.00, 0.82),
('Jaipur Patna Express', 'Express', 'Jaipur Junction', 'Patna Junction', 400, 280.00, 0.82),

-- JODHPUR BASED
('Jodhpur Delhi Intercity', 'Intercity', 'Jodhpur Junction', 'New Delhi Junction', 250, 180.00, 0.80),
('Jodhpur Mumbai Express', 'Express', 'Jodhpur Junction', 'Mumbai Central', 450, 300.00, 0.82),
('Jodhpur Jaisalmer Express', 'Express', 'Jodhpur Junction', 'Jaisalmer Junction', 200, 100.00, 0.75),
('Jodhpur Bikaner Intercity', 'Intercity', 'Jodhpur Junction', 'Bikaner Junction', 200, 90.00, 0.70),
('Jodhpur Barmer Express', 'Express', 'Jodhpur Junction', 'Barmer Junction', 200, 100.00, 0.75),
('Jodhpur Ahmedabad Express', 'Express', 'Jodhpur Junction', 'Ahmedabad Junction', 350, 220.00, 0.80),
('Jodhpur Kolkata Express', 'Express', 'Jodhpur Junction', 'Howrah Junction', 400, 350.00, 0.85),
('Jodhpur Hyderabad Express', 'Express', 'Jodhpur Junction', 'Hyderabad Deccan', 350, 320.00, 0.85),

-- BIKANER BASED
('Bikaner Delhi Express', 'Express', 'Bikaner Junction', 'New Delhi Junction', 350, 180.00, 0.80),
('Bikaner Kolkata Express', 'Express', 'Bikaner Junction', 'Howrah Junction', 450, 380.00, 0.85),
('Bikaner Mumbai Express', 'Express', 'Bikaner Junction', 'Mumbai Central', 450, 320.00, 0.82),
('Bikaner Jammu Express', 'Express', 'Bikaner Junction', 'Jammu Tawi', 350, 250.00, 0.82),
('Bikaner Guwahati Express', 'Express', 'Bikaner Junction', 'Guwahati Junction', 400, 450.00, 0.85),
('Bikaner Jodhpur Intercity', 'Intercity', 'Bikaner Junction', 'Jodhpur Junction', 200, 90.00, 0.70),

-- UDAIPUR BASED
('Udaipur Delhi Express', 'Express', 'Udaipur City', 'New Delhi Junction', 350, 220.00, 0.82),
('Udaipur Mumbai Express', 'Express', 'Udaipur City', 'Mumbai Central', 400, 280.00, 0.82),
('Udaipur Ahmedabad Intercity', 'Intercity', 'Udaipur City', 'Ahmedabad Junction', 250, 120.00, 0.75),
('Udaipur Jaipur Intercity', 'Intercity', 'Udaipur City', 'Jaipur Junction', 200, 100.00, 0.72),
('Udaipur Chittorgarh Passenger', 'Passenger', 'Udaipur City', 'Chittorgarh Junction', 150, 40.00, 0.60),
('Udaipur Hyderabad Express', 'Express', 'Udaipur City', 'Hyderabad Deccan', 350, 300.00, 0.82),

-- AJMER BASED
('Ajmer Delhi Express', 'Express', 'Ajmer Junction', 'New Delhi Junction', 350, 180.00, 0.80),
('Ajmer Mumbai Express', 'Express', 'Ajmer Junction', 'Mumbai Central', 400, 280.00, 0.82),
('Ajmer Jammu Express', 'Express', 'Ajmer Junction', 'Jammu Tawi', 350, 280.00, 0.82),
('Ajmer Kolkata Express', 'Express', 'Ajmer Junction', 'Howrah Junction', 400, 350.00, 0.85),
('Ajmer Jaipur Intercity', 'Intercity', 'Ajmer Junction', 'Jaipur Junction', 200, 60.00, 0.65),
('Ajmer Jodhpur Intercity', 'Intercity', 'Ajmer Junction', 'Jodhpur Junction', 200, 90.00, 0.70),

-- KOTA BASED
('Kota Delhi Express', 'Express', 'Kota Junction', 'New Delhi Junction', 350, 180.00, 0.80),
('Kota Mumbai Express', 'Express', 'Kota Junction', 'Mumbai Central', 400, 260.00, 0.82),
('Kota Jaipur Intercity', 'Intercity', 'Kota Junction', 'Jaipur Junction', 200, 80.00, 0.70),
('Kota Chennai Express', 'Express', 'Kota Junction', 'Chennai Central', 400, 350.00, 0.85),
('Kota Patna Express', 'Express', 'Kota Junction', 'Patna Junction', 350, 280.00, 0.82),
('Kota Bhopal Intercity', 'Intercity', 'Kota Junction', 'Bhopal Junction', 250, 120.00, 0.75),

-- ALWAR / BHARATPUR BASED
('Alwar Delhi Intercity', 'Intercity', 'Alwar Junction', 'New Delhi Junction', 200, 80.00, 0.70),
('Bharatpur Agra Intercity', 'Intercity', 'Bharatpur Junction', 'Agra Cantt', 200, 60.00, 0.65),
('Bharatpur Delhi Express', 'Express', 'Bharatpur Junction', 'New Delhi Junction', 300, 120.00, 0.78),

-- JAISALMER BASED
('Jaisalmer Delhi Express', 'Express', 'Jaisalmer Junction', 'New Delhi Junction', 300, 280.00, 0.82),
('Jaisalmer Jodhpur Intercity', 'Intercity', 'Jaisalmer Junction', 'Jodhpur Junction', 150, 100.00, 0.70),

-- BARMER BASED
('Barmer Jodhpur Intercity', 'Intercity', 'Barmer Junction', 'Jodhpur Junction', 150, 100.00, 0.70),
('Barmer Delhi Express', 'Express', 'Barmer Junction', 'New Delhi Junction', 300, 280.00, 0.82),
('Barmer Mumbai Express', 'Express', 'Barmer Junction', 'Mumbai Central', 350, 320.00, 0.82),

-- SRI GANGANAGAR BASED
('Sri Ganganagar Delhi Express', 'Express', 'Sri Ganganagar Junction', 'New Delhi Junction', 300, 200.00, 0.80),
('Sri Ganganagar Mumbai Express', 'Express', 'Sri Ganganagar Junction', 'Mumbai Central', 350, 350.00, 0.82),
('Sri Ganganagar Bikaner Intercity', 'Intercity', 'Sri Ganganagar Junction', 'Bikaner Junction', 200, 80.00, 0.68),
('Sri Ganganagar Jaipur Express', 'Express', 'Sri Ganganagar Junction', 'Jaipur Junction', 300, 180.00, 0.78),

-- CHITTORGARH BASED
('Chittorgarh Udaipur Passenger', 'Passenger', 'Chittorgarh Junction', 'Udaipur City', 150, 40.00, 0.60),
('Chittorgarh Kota Intercity', 'Intercity', 'Chittorgarh Junction', 'Kota Junction', 200, 80.00, 0.70),
('Chittorgarh Delhi Express', 'Express', 'Chittorgarh Junction', 'New Delhi Junction', 300, 220.00, 0.80),
('Chittorgarh Mumbai Express', 'Express', 'Chittorgarh Junction', 'Mumbai Central', 350, 260.00, 0.82),

-- ABU ROAD BASED
('Abu Road Ahmedabad Intercity', 'Intercity', 'Abu Road Junction', 'Ahmedabad Junction', 200, 100.00, 0.70),
('Abu Road Delhi Express', 'Express', 'Abu Road Junction', 'New Delhi Junction', 300, 250.00, 0.80),
('Abu Road Mumbai Express', 'Express', 'Abu Road Junction', 'Mumbai Central', 350, 220.00, 0.80),

-- BHILWARA BASED
('Bhilwara Jaipur Intercity', 'Intercity', 'Bhilwara Junction', 'Jaipur Junction', 200, 90.00, 0.70),
('Bhilwara Ajmer Intercity', 'Intercity', 'Bhilwara Junction', 'Ajmer Junction', 200, 70.00, 0.68),
('Bhilwara Mumbai Express', 'Express', 'Bhilwara Junction', 'Mumbai Central', 350, 260.00, 0.82),

-- SAWAI MADHOPUR BASED
('Sawai Madhopur Jaipur Intercity', 'Intercity', 'Sawai Madhopur Junction', 'Jaipur Junction', 200, 80.00, 0.70),
('Sawai Madhopur Kota Intercity', 'Intercity', 'Sawai Madhopur Junction', 'Kota Junction', 200, 70.00, 0.68);