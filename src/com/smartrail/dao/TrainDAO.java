package com.smartrail.dao;

import com.smartrail.model.Trains;

import java.sql.*;

public class TrainDAO {
    private Connection con;

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
    public void getAllTrains() {
        try {
            String query = "SELECT * FROM trains";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("\n===== TRAIN LIST =====");
            System.out.printf("%-5s %-20s %-15s %-15s %-10s\n",
                    "ID", "Train Name", "Source", "Destination", "Seats");

            while (rs.next()) {
                System.out.printf("%-5d %-20s %-15s %-15s %-10d\n",
                        rs.getInt("train_id"),
                        rs.getString("train_name"),
                        rs.getString("source"),
                        rs.getString("destination"),
                        rs.getInt("available_seats"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTotalSeats(int trainId) {
        int seats = 0;
        try {
            String query = "SELECT total_seats FROM trains WHERE train_id=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, trainId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                seats = rs.getInt("total_seats");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return seats;
    }

    public int getAvailableSeats(int trainId, Date journeyDate) {
        int availableSeats = 0;

        try {
            String query = "SELECT available_seats FROM train_schedule WHERE train_id = ? AND journey_date = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, trainId);
            ps.setDate(2, journeyDate);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                availableSeats = rs.getInt("available_seats");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return availableSeats;
    }

    public void updateSeats(int trainId, Date date, int seatsBooked) {
        try {
            String query = "UPDATE train_schedule SET available_seats = available_seats - ? WHERE train_id=? AND journey_date=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, seatsBooked);
            ps.setInt(2, trainId);
            ps.setDate(3, date);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restoreSeats(int trainId, Date date, int seatsToRestore) {
        try {
            String query = "UPDATE train_schedule SET available_seats = available_seats + ? WHERE train_id=? AND journey_date=?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, seatsToRestore);
            ps.setInt(2, trainId);
            ps.setDate(3, date);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchTrain(String departure, String destination) {
        try {
            String query = "SELECT * FROM trains WHERE LOWER(source) = LOWER(?) AND LOWER(destination) = LOWER(?)";
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
                System.out.println("Source: " + rs.getString("source") + " -> " + rs.getString("destination"));
                System.out.println("Total Seats: " + rs.getInt("total_seats"));
            }

            if (!found) {
                System.out.println("No trains found for this route.");
            }

            System.out.println("-----------------------------------");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createScheduleIfNotExists(int trainId, Date date, int totalSeats) {
        try {
            String checkQuery = "SELECT * FROM train_schedule WHERE train_id=? AND journey_date=?";
            PreparedStatement check = con.prepareStatement(checkQuery);

            check.setInt(1, trainId);
            check.setDate(2, date);

            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                String insertQuery = "INSERT INTO train_schedule(train_id, journey_date, available_seats) VALUES (?, ?, ?)";
                PreparedStatement insert = con.prepareStatement(insertQuery);

                insert.setInt(1, trainId);
                insert.setDate(2, date);
                insert.setInt(3, totalSeats);

                insert.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void generateUpcomingSchedules(int daysAhead) {
        try {
            String query = """
            INSERT INTO train_schedule (train_id, journey_date, available_seats)
            SELECT t.train_id, DATE_ADD(CURDATE(), INTERVAL ? DAY), t.total_seats
            FROM trains t
            WHERE NOT EXISTS (
                SELECT 1 FROM train_schedule ts
                WHERE ts.train_id = t.train_id
                AND ts.journey_date = DATE_ADD(CURDATE(), INTERVAL ? DAY)
            )
        """;
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, daysAhead);
            ps.setInt(2, daysAhead);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
