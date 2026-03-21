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
                    PreparedStatement ps2 = con.prepareStatement(insertQuery);
                    ps2.setInt(1, trainId);
                    ps2.setInt(2, passengerId);
                    ps2.setInt(3, seats);

                    ps2.executeUpdate();

                    // Step 3: Update seats
                    String updateQuery = "UPDATE trains SET available_seats = available_seats - ? WHERE train_id = ?";
                    PreparedStatement ps3 = con.prepareStatement(updateQuery);
                    ps3.setInt(1, seats);
                    ps3.setInt(2, trainId);
                    ps3.executeUpdate();

                    System.out.println("✅ Booking successful!");

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
