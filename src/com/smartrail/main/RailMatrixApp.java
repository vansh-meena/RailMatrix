package com.smartrail.main;

import com.smartrail.dao.StationDAO;
import com.smartrail.model.Station;
import com.smartrail.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class RailMatrixApp {
    public static void main(String[] args) throws SQLException {
        try {
            Connection con = DBConnection.getConnection();

            StationDAO dao = new StationDAO(con);
            //INSERTING
//            Station s1 = new Station(1001, "Delhi Junction", "Delhi");
//            Station s2 = new Station(1002, "Jaipur Junction", "Jaipur");
//
//            dao.addStation(s1);
//            dao.addStation(s2);
            //FETCHING
            System.out.println("\nAll Stations:");
            dao.getAllStations();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
