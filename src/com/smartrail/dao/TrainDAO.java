package com.smartrail.dao;

import com.smartrail.model.Trains;

import java.sql.*;

public class TrainDAO {
    private static Connection con;

    public TrainDAO(Connection con) {
        this.con = con;
    }

    // INSERT
    public void addTrain(Trains train) {
        try {
            String query = "INSERT INTO trains(train_name, departure, destination, total_seats, available_seats) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, train.getTrainName());
            ps.setString(2, train.getDeparture());
            ps.setString(3, train.getDestination());
            ps.setInt(4, train.getTotalSeats());
            ps.setInt(5, train.getAvailableSeats());

            ps.executeUpdate();
            System.out.println("Train added successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // FETCH
    public static void getAllTrains() {
        try {
            String query = "SELECT * FROM trains";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                System.out.println(
                        rs.getInt("train_id") + " | " +
                                rs.getString("train_name") + " | " +
                                rs.getString("departure") + " -> " +
                                rs.getString("destination") + " | Available Seats: " +
                                rs.getInt("available_seats")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getAvailableSeats(int trainId) {
        try {
            String query = "SELECT available_seats FROM trains WHERE train_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, trainId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Available Seats: " + rs.getInt("available_seats"));
            } else {
                System.out.println("Train not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
