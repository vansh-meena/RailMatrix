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

            System.out.println("\n===== TRAIN LIST =====");
            System.out.printf("%-5s %-20s %-15s %-15s %-10s\n",
                    "ID", "Train Name", "Source", "Destination", "Seats");

            while (rs.next()) {
                System.out.printf("%-5d %-20s %-15s %-15s %-10d\n",
                        rs.getInt("id"),
                        rs.getString("train_name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("available_seats"));
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

    public void searchTrain(String departure, String destination) {
        try {
            String query = "SELECT * FROM trains WHERE departure = ? AND destination = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, departure);
            ps.setString(2, destination);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            System.out.println("\n===== SEARCH RESULTS =====");

            while (rs.next()) {
                found = true;

                System.out.println("-----------------------------------");
                System.out.println("Train ID: " + rs.getInt("train_id"));
                System.out.println("Train Name: " + rs.getString("train_name"));
                System.out.println("Route: " + rs.getString("departure") + " -> " + rs.getString("destination"));
                System.out.println("Available Seats: " + rs.getInt("available_seats"));
            }

            if (!found) {
                System.out.println("❌ No trains found for this route.");
            }

            System.out.println("-----------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
