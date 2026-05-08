package com.smartrail.dao;

import com.smartrail.model.Booking;

import java.sql.*;

public class BookingDAO {
    private Connection con;

    public BookingDAO(Connection con) {
        this.con = con;
    }

    public int bookSeats(int userId, int trainId, Date journeyDate, int seats) {
        try {
            // Step 1: Check available seats
            String checkQuery = "SELECT available_seats FROM train_schedule WHERE train_id = ? AND journey_date = ?";
            PreparedStatement ps1 = con.prepareStatement(checkQuery);
            ps1.setInt(1, trainId);

            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                int available = rs.getInt("available_seats");

                if (available >= seats) {

                    // Step 2: Insert booking
                    String insertQuery = "INSERT INTO bookings(user_id, train_id, journey_date, total_passengers) VALUES (?, ?, ?, ?)";
                    PreparedStatement ps2 = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                    ps2.setInt(1, userId);
                    ps2.setInt(2, trainId);
                    ps2.setDate(3, journeyDate);
                    ps2.setInt(4, seats);

                    ps2.executeUpdate();

                    ResultSet rs2 = ps2.getGeneratedKeys();
                    int bookingId = -1;

                    if (rs2.next()) {
                        bookingId = rs2.getInt(1);
                    }

                    // Step 3: Update seats
                    String updateQuery = "UPDATE train_schedule SET available_seats = available_seats - ? WHERE train_id = ? AND journey_date = ?";
                    PreparedStatement ps3 = con.prepareStatement(updateQuery);
                    ps3.setInt(1, seats);
                    ps3.setInt(2, trainId);
                    ps3.executeUpdate();

                    //FETCHING PASSENGER NAME
                    String pQuery = "SELECT passenger_name FROM passengers WHERE passenger_id = ?";
                    PreparedStatement psP = con.prepareStatement(pQuery);
                    psP.setInt(1, userId);

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

//                    System.out.println("Booking successful!");
                    System.out.println("\n===== BOOKING CONFIRMED =====");
                    System.out.println("Booking ID: " + bookingId);
                    System.out.println("Passenger: " + passengerName);
                    System.out.println("Train: " + trainName);
                    System.out.println("Route: " + departure + " -> " + destination);
                    System.out.println("Seats Booked: " + seats);
                    System.out.println("============================");

                } else {
                    System.out.println("Not enough seats available!");
                }

            } else {
                System.out.println("Train not found!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userId;
    }

    public void deleteBooking(int bookingId) {
        try {
            // delete passengers first
            String pQuery = "DELETE FROM passengers WHERE booking_id = ?";
            PreparedStatement ps1 = con.prepareStatement(pQuery);
            ps1.setInt(1, bookingId);
            ps1.executeUpdate();

            // delete booking
            String bQuery = "DELETE FROM bookings WHERE booking_id = ?";
            PreparedStatement ps2 = con.prepareStatement(bQuery);
            ps2.setInt(1, bookingId);
            ps2.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getBookingsByUser(int userId) {
        try {
            String query = "SELECT * FROM bookings WHERE user_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                found = true;
                System.out.println("\nBooking ID: " + rs.getInt("booking_id"));
                System.out.println("Train ID: " + rs.getInt("train_id"));
                System.out.println("Seats: " + rs.getInt("seats_booked"));
                System.out.println("Journey Date: " + rs.getDate("journey_date"));
            }

            if (!found) {
                System.out.println("No bookings found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Booking getBookingById(int bookingId) {
        Booking booking = null;

        try {
            String query = "SELECT * FROM bookings WHERE booking_id = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setInt(1, bookingId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                booking = new Booking();
                booking.setBookingId(rs.getInt("booking_id"));
                booking.setTrainId(rs.getInt("train_id"));
                booking.setUserId(rs.getInt("user_id"));
                booking.setSeatsBooked(rs.getInt("seats_booked"));
                booking.setJourneyDate(rs.getDate("journey_date"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return booking;
    }
}
