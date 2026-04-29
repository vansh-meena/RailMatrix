package com.smartrail.dao;

import com.smartrail.model.Passenger;

import java.sql.*;

public class PassengerDAO {

    private Connection con;

    public PassengerDAO(Connection con) {
        this.con = con;
    }

    public int addPassenger(Passenger p, int bookingId, int seatNo) {
        try {
            String query = "INSERT INTO passengers(booking_id, passenger_name, age, gender, seat_number) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, bookingId);
            ps.setString(2, p.getPassengerName());
            ps.setInt(3, p.getAge());
            ps.setString(4, p.getGender());
            ps.setInt(5, seatNo);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
