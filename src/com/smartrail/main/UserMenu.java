package com.smartrail.main;

import com.smartrail.dao.*;
import com.smartrail.model.Booking;
import com.smartrail.model.Passenger;
import com.smartrail.model.Station;
import com.smartrail.service.RouteService;
import com.smartrail.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class UserMenu {

    private int userId;

    public UserMenu(int userId) {
        this.userId = userId;
    }

    public void start() throws Exception {

        Connection con = DBConnection.getConnection();
        TrainDAO trainDAO = new TrainDAO(con);
        BookingDAO bookingDAO = new BookingDAO(con);
        PassengerDAO passengerDAO = new PassengerDAO(con);
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== USER MENU =====");
            System.out.println("1. Book Ticket");
            System.out.println("2. Cancel Booking");
            System.out.println("3. View Booking History");
            System.out.println("4. Check Available Seats");
            System.out.println("5. Search Train by Route");
            System.out.println("6. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1: {
                    System.out.print("Enter Train ID: ");
                    int trainId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter number of passengers: ");
                    int numberOfPassengers = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter journey date (YYYY-MM-DD): ");
                    String dateInput = sc.nextLine();
                    Date journeyDate = Date.valueOf(dateInput);

                    // STEP 1: Get total seats
                    int totalSeats = trainDAO.getTotalSeats(trainId);

                    // STEP 2: Ensure schedule exists
                    trainDAO.createScheduleIfNotExists(trainId, journeyDate, totalSeats);

                    // STEP 3: Check available seats
                    int availableSeats = trainDAO.getAvailableSeats(trainId, journeyDate);

                    if (availableSeats < numberOfPassengers) {
                        System.out.println("Not enough seats available!");
                        break;
                    }

                    // STEP 4: Create booking
                    int bookingId = bookingDAO.bookSeats(userId, trainId, journeyDate, numberOfPassengers);

                    // STEP 5: Add passengers
                    int seatNo = 1;

                    for (int i = 0; i < numberOfPassengers; i++) {

                        System.out.println("\nPassenger " + (i + 1));

                        System.out.print("Enter name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter age: ");
                        int age = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter gender: ");
                        String gender = sc.nextLine();

                        Passenger p = new Passenger(name, age, gender);

                        passengerDAO.addPassenger(p, bookingId, seatNo);

                        seatNo++;
                    }

                    // STEP 6: Deduct seats
                    trainDAO.updateSeats(trainId, journeyDate, numberOfPassengers);

                    System.out.println("\nBooking Successful!");
                    break;
                }

                case 2: {
                    System.out.print("Enter Booking ID to cancel: ");
                    int bookingId = sc.nextInt();
                    sc.nextLine();

                    // STEP 1: Fetch booking
                    Booking booking = bookingDAO.getBookingById(bookingId);

                    if (booking == null) {
                        System.out.println("Booking not found!");
                        break;
                    }

                    // OPTIONAL: check ownership
                    if (booking.getUserId() != userId) {
                        System.out.println("You can only cancel your own bookings!");
                        break;
                    }

                    // STEP 2: Restore seats
                    trainDAO.restoreSeats(
                            booking.getTrainId(),
                            booking.getJourneyDate(),
                            booking.getSeatsBooked()
                    );

                    // STEP 3: Delete booking + passengers
                    bookingDAO.deleteBooking(bookingId);

                    System.out.println("Booking cancelled successfully!");
                    break;
                }
                case 3:
                    bookingDAO.getBookingsByUser(userId);
                    break;

                case 4:

                    System.out.print("Enter Train ID: ");
                    int trainId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter journey date (YYYY-MM-DD): ");
                    Date journeyDate = Date.valueOf(sc.nextLine());

                    int seats = trainDAO.getAvailableSeats(trainId, journeyDate);

                    System.out.println("Available Seats: " + seats);
                    break;

                case 5:

                    System.out.print("Enter source station name: ");
                    String source = sc.nextLine();

                    System.out.print("Enter destination station name: ");
                    String dest = sc.nextLine();

                    int sourceId = StationDAO.getStationId(source);
                    int destId = StationDAO.getStationId(dest);

                    RouteService routeService = new RouteService(new RouteDAO(con));

                    List<Integer> path = routeService.getShortestPath(sourceId, destId);

                    if (path == null) {
                        System.out.println("No route found!");
                        break;
                    }

                    System.out.println("\nShortest Route:");

                    for (int id : path) {
                        System.out.print(StationDAO.getStationId(String.valueOf(id)) + " -> ");
                    }

                    System.out.println("END");

                    break;

                case 6:
                    System.out.println("Logged out!");
                    return;
            }
        }
    }
}
