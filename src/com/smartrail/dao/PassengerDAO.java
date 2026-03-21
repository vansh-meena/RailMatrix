package com.smartrail.dao;

import com.smartrail.model.Passenger;
import java.sql.*;

public class PassengerDAO {

    private Connection con;

    public PassengerDAO(Connection con) {
        this.con = con;
    }

    public int addPassenger(Passenger p) {
        try {
            String query = "INSERT INTO passengers(passenger_name, age, gender) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, p.getPassenger_name());
            ps.setInt(2, p.getAge());
            ps.setString(3, p.getGender());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // return passenger_id
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
