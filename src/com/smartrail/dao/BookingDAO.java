package com.smartrail.dao;

import com.smartrail.model.Booking;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class BookingDAO {
    private Connection con;

    public BookingDAO(Connection con) {
        this.con = con;
    }

    public int bookSeats(int userId, int trainId, Date journeyDate, int seats) {
        try {
            con.setAutoCommit(false); // START TRANSACTION

            String checkQuery = "SELECT (available_gn + available_tq + available_ld + available_hq) AS available_seats FROM schedule_seats WHERE train_id = ? AND journey_date = ?";
            PreparedStatement ps1 = con.prepareStatement(checkQuery);
            ps1.setInt(1, trainId);
            ps1.setDate(2, journeyDate);
            ResultSet rs = ps1.executeQuery();

            int available = 0;
            if (rs.next()) {
                available = rs.getInt("available_seats");
            } else {
                String capQuery = "SELECT COALESCE(SUM(total_seats), 0) AS cap FROM train_classes WHERE train_id = ?";
                PreparedStatement psCap = con.prepareStatement(capQuery);
                psCap.setInt(1, trainId);
                ResultSet rsCap = psCap.executeQuery();
                if (rsCap.next()) available = rsCap.getInt("cap");
            }

            if (available >= seats) {

                    // Generate PNR: RM + YY + MM + 6 random digits
                    String pnr = generatePNR();

                    String insertQuery = "INSERT INTO bookings(pnr, user_id, train_id, journey_date, total_passengers) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement ps2 = con.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
                    ps2.setString(1, pnr);
                    ps2.setInt(2, userId);
                    ps2.setInt(3, trainId);
                    ps2.setDate(4, journeyDate);
                    ps2.setInt(5, seats);
                    ps2.executeUpdate();

                    ResultSet rs2 = ps2.getGeneratedKeys();
                    int bookingId = -1;
                    if (rs2.next()) bookingId = rs2.getInt(1);

                    String updateQuery = "UPDATE schedule_seats SET available_gn = GREATEST(available_gn - ?, 0) WHERE train_id = ? AND journey_date = ?";
                    PreparedStatement ps3 = con.prepareStatement(updateQuery);
                    ps3.setInt(1, seats);
                    ps3.setInt(2, trainId);
                    ps3.setDate(3, journeyDate);
                    ps3.executeUpdate();

                    con.commit(); // COMMIT

                    // fetch display info
                    String pQuery = "SELECT passenger_name FROM passengers WHERE passenger_id = ?";
                    PreparedStatement psP = con.prepareStatement(pQuery);
                    psP.setInt(1, userId);
                    ResultSet rsP = psP.executeQuery();
                    String passengerName = rsP.next() ? rsP.getString("passenger_name") : "";

                    String tQuery = "SELECT train_name, departure, destination FROM trains WHERE train_id = ?";
                    PreparedStatement psT = con.prepareStatement(tQuery);
                    psT.setInt(1, trainId);
                    ResultSet rsT = psT.executeQuery();
                    String trainName = "", departure = "", destination = "";
                    if (rsT.next()) {
                        trainName   = rsT.getString("train_name");
                        departure   = rsT.getString("departure");
                        destination = rsT.getString("destination");
                    }

                    System.out.println("\n===== BOOKING CONFIRMED =====");
                    System.out.println("Booking ID : " + bookingId);
                    System.out.println("Passenger  : " + passengerName);
                    System.out.println("Train      : " + trainName);
                    System.out.println("Route      : " + departure + " -> " + destination);
                    System.out.println("Seats      : " + seats);
                    System.out.println("=============================");

                    return bookingId;

                } else {
                    System.out.println("Not enough seats available!");
                }
            } else {
                System.out.println("No seats available or train not found!");
            }

            con.rollback(); // ROLLBACK if we didn't return

        } catch (Exception e) {
            try { con.rollback(); } catch (Exception ex) { ex.printStackTrace(); }
            e.printStackTrace();
        } finally {
            try { con.setAutoCommit(true); } catch (Exception e) { e.printStackTrace(); }
        }
        return -1;
    }

    public void deleteBooking(int bookingId, double refundAmount) {
        try {
            // Mark as cancelled instead of deleting
            String query = "UPDATE bookings SET status = 'CANCELLED', " +
                    "cancelled_at = CURRENT_TIMESTAMP, " +
                    "refund_amount = ? WHERE booking_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setDouble(1, refundAmount);
            ps.setInt(2, bookingId);
            ps.executeUpdate();
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
                System.out.println("Seats: " + rs.getInt("total_passengers"));
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

    // ── PNR Generator ──────────────────────────────────────────────
    // Format: RM + YY + MM + 6 random digits  →  e.g. RM260518839201
    public static String generatePNR() {
        LocalDateTime now = LocalDateTime.now();
        String yr   = now.format(DateTimeFormatter.ofPattern("yy"));
        String mo   = now.format(DateTimeFormatter.ofPattern("MM"));
        String rand = String.format("%06d", new Random().nextInt(1_000_000));
        return "RM" + yr + mo + rand;
    }
}
