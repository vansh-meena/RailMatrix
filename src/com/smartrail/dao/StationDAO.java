package com.smartrail.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import com.smartrail.model.Station;

public class StationDAO {

    private static Connection con;

//Non-parameterized Constructor
    public StationDAO () {}

//Parameterized constructor
    public StationDAO(Connection con) {
        this.con = con;
    }

    public void addStation(Station station) {
        try {
            String query = "INSERT INTO stations(station_name, city) VALUES (?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, station.getStationName());
            ps.setString(2, station.getStationCity());

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

    public Map<Integer, String> getStationIdNameMap() {

        Map<Integer, String> map = new HashMap<>();

        try {
            String query = "SELECT station_id, station_name FROM stations";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                map.put(rs.getInt("station_id"), rs.getString("station_name"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public String getStationName(String stationName) {
        try {
            String query = "SELECT station_name FROM stations WHERE station_name = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, stationName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("station_name");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Unknown";
    }

    public static int getStationId(String stationName) {

        try {
            String query = "SELECT station_id FROM stations WHERE station_name = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, stationName);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("station_id");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }
}

