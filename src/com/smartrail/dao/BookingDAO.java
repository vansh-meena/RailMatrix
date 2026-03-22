package com.smartrail.dao;

import java.sql.*;

public class BookingDAO {
    private Connection con;

    public BookingDAO(Connection con) {
        this.con = con;
    }

    public void bookSeats(int trainId, int passengerId, int seats) {
        try {
            // Step 1: Check available seats
            String checkQuery = "SELECT available_seats FROM trains WHERE train_id = ?";
            PreparedStatement ps1 = con.prepareStatement(checkQuery);
            ps1.setInt(1, trainId);

            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                int available = rs.getInt("available_seats");

                if (available >= seats) {

                    // Step 2: Insert booking
                    String insertQuery = "INSERT INTO bookings(train_id, passenger_id, seats_booked) VALUES (?, ?, ?)";
                    PreparedStatement ps2 = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                    ps2.setInt(1, trainId);
                    ps2.setInt(2, passengerId);
                    ps2.setInt(3, seats);

                    ps2.executeUpdate();

                    ResultSet rs2 = ps2.getGeneratedKeys();
                    int bookingId = -1;

                    if (rs2.next()) {
                        bookingId = rs2.getInt(1);
                    }

                    // Step 3: Update seats
                    String updateQuery = "UPDATE trains SET available_seats = available_seats - ? WHERE train_id = ?";
                    PreparedStatement ps3 = con.prepareStatement(updateQuery);
                    ps3.setInt(1, seats);
                    ps3.setInt(2, trainId);
                    ps3.executeUpdate();

                    //FETCHING PASSENGER NAME
                    String pQuery = "SELECT passenger_name FROM passengers WHERE passenger_id = ?";
                    PreparedStatement psP = con.prepareStatement(pQuery);
                    psP.setInt(1, passengerId);

                    ResultSet rsP = psP.executeQuery();
                    String passengerName = "";

                    if (rsP.next()) {
                        passengerName = rsP.getString("passenger_name");
                    }

                    //FETCHING TRAIN DETAILS
                    String tQuery = "SELECT train_name, departure, destination FROM trains WHERE train_id = ?";
                    PreparedStatement psT = con.prepareStatement(tQuery);
                    psT.setInt(1, trainId);

                    ResultSet rsT = psT.executeQuery();

                    String trainName = "", departure = "", destination = "";

                    if (rsT.next()) {
                        trainName = rsT.getString("train_name");
                        departure = rsT.getString("departure");
                        destination = rsT.getString("destination");
                    }

//                    System.out.println("✅ Booking successful!");
                    System.out.println("\n===== BOOKING CONFIRMED =====");
                    System.out.println("Booking ID: " + bookingId);
                    System.out.println("Passenger: " + passengerName);
                    System.out.println("Train: " + trainName);
                    System.out.println("Route: " + departure + " -> " + destination);
                    System.out.println("Seats Booked: " + seats);
                    System.out.println("============================");

                } else {
                    System.out.println("❌ Not enough seats available!");
                }

            } else {
                System.out.println("❌ Train not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelBooking(int bookingId) {
        try {
            // Step 1: Get booking details
            String getQuery = "SELECT train_id, seats_booked FROM bookings WHERE booking_id = ?";
            PreparedStatement ps1 = con.prepareStatement(getQuery);
            ps1.setInt(1, bookingId);

            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                int trainId = rs.getInt("train_id");
                int seats = rs.getInt("seats_booked");

                // Step 2: Restore seats
                String updateQuery = "UPDATE trains SET available_seats = available_seats + ? WHERE train_id = ?";
                PreparedStatement ps2 = con.prepareStatement(updateQuery);
                ps2.setInt(1, seats);
                ps2.setInt(2, trainId);
                ps2.executeUpdate();

                // Step 3: Delete booking
                String deleteQuery = "DELETE FROM bookings WHERE booking_id = ?";
                PreparedStatement ps3 = con.prepareStatement(deleteQuery);
                ps3.setInt(1, bookingId);
                ps3.executeUpdate();

                System.out.println("✅ Booking cancelled and seats restored!");

            } else {
                System.out.println("❌ Booking not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
