package com.smartrail.dao;

import java.sql.*;
import com.smartrail.model.Station;
import com.smartrail.util.DBConnection;

public class StationDAO {

    private Connection con;

//Non-parameterized Constructor
    public StationDAO () {}

//Parameterized constructor
    public StationDAO(Connection con) {
        this.con = con;
    }

    public void addStation(Station station) {
        try {
            String query = "INSERT INTO stations(station_id, station_name, city) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, station.getStationId());
            ps.setString(2, station.getStationName());
            ps.setString(3, station.getStationCity());

            ps.executeUpdate();
            System.out.println("Station added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAllStations() {
        try {
            String query = "SELECT * FROM stations";
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println(
                        rs.getInt("station_id") + " | " +
                                rs.getString("station_name") + " | " +
                                rs.getString("city")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

