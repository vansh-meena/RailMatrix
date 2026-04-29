package com.smartrail.main;

import java.sql.Date;
import java.util.*;

import com.smartrail.dao.*;
import com.smartrail.model.Passenger;
import com.smartrail.model.Route;
import com.smartrail.model.Station;
import com.smartrail.model.Trains;
import com.smartrail.dao.RouteDAO;
import com.smartrail.service.GraphBuilder;
import com.smartrail.service.ShortestPathService;
import com.smartrail.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class RailMatrixApp {
    public static void main(String[] args) throws SQLException {
        int currentUserId = -1;
        try {
            Connection con = DBConnection.getConnection();
            StationDAO dao = new StationDAO(con);
            TrainDAO trainDAO = new TrainDAO(con);
            BookingDAO bookingDAO = new BookingDAO(con);
            PassengerDAO passengerDAO = new PassengerDAO(con);
            RouteDAO routeDAO = new RouteDAO(con);

            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n===== RailMatrix Menu =====");
                System.out.println("1. Add Station");
                System.out.println("2. View Stations");
                System.out.println("3. Add Train");
                System.out.println("4. View Trains");
                System.out.println("5. Book Ticket");
                System.out.println("6. Check Available Seats");
                System.out.println("7. Cancel Booking");
                System.out.println("8. View Booking History");
                System.out.println("9. Search Train by Route");
                System.out.println("10. Test Routes Graph");
                System.out.println("11. Find Shortest Route");
                System.out.println("12. Exit");
                System.out.print("Enter choice: ");

                int choice = sc.nextInt();
                sc.nextLine(); // clear buffer

                switch (choice) {

                    case 1:
                        System.out.print("Enter Station Name: ");
                        String station_name = sc.nextLine();

                        System.out.print("Enter City: ");
                        String city = sc.nextLine();

                        Station s = new Station(station_name, city);
                        dao.addStation(s);
                        break;

                    case 2:
                        System.out.println("\nAll Stations:");
                        dao.getAllStations();
                        break;

                    case 3: {
                        if (currentUserId == -1) {
                            System.out.println("Please login first!");
                            break;
                        }

                        // STEP 1: Input date
                        System.out.print("Enter Train ID: ");
                        int trainId = sc.nextInt();
                        sc.nextLine(); // consume newline

                        System.out.print("Enter number of passengers: ");
                        int numberOfPassengers = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter journey date (YYYY-MM-DD): ");
                        String dateInput = sc.nextLine();
                        Date journeyDate = Date.valueOf(dateInput);
                        int totalSeats = trainDAO.getTotalSeats(trainId);

                        // STEP 2: Ensure schedule exists
                        trainDAO.createScheduleIfNotExists(trainId, journeyDate, totalSeats);

                        // STEP 3: Check seats
                        int availableSeats = trainDAO.getAvailableSeats(trainId, journeyDate);

                        if (availableSeats < numberOfPassengers) {
                            System.out.println("Not enough seats available!");
                            return;
                        }

                        // STEP 4: Create booking
                        int bookingId = bookingDAO.bookSeats(currentUserId, trainId, journeyDate, numberOfPassengers);

                        // STEP 5: Add passengers
                        int seatNo = 1;

                        for (int i = 0; i < numberOfPassengers; i++) {

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

                        System.out.println("Booking Successful!");

//                        System.out.print("Enter Train Name: ");
//                        String tname = sc.nextLine();
//
//                        System.out.print("Enter Departure: ");
//                        String dep = sc.nextLine();
//
//                        System.out.print("Enter Destination: ");
//                        String dest = sc.nextLine();
//
//                        System.out.print("Enter Total Seats: ");
//                        int seats = sc.nextInt();
//                        sc.nextLine();
//
//                        Trains t = new Trains(tname, dep, dest, seats);
//                        trainDAO.addTrain(t);
                        break;
                    }

                    case 4:
                        System.out.println("\nAll Trains:");
                        TrainDAO.getAllTrains();
                        break;

                    case 5:
                        sc.nextLine(); // clear buffer

                        System.out.print("Enter Passenger Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Gender: ");
                        String gender = sc.nextLine();

                        System.out.print("Enter Train ID: ");
                        int trainId = sc.nextInt();

                        System.out.println("Enter Journey Date: ");
                        String journeyDate = sc.nextLine();

                        System.out.print("Enter number of seats: ");
                        int seats = sc.nextInt();

                        // VALIDATION FIRST
                        if (seats <= 0) {
                            System.out.println("Invalid number of seats!");
                            break;
                        }
                        if (trainId <= 0) {
                            System.out.println("Invalid Train ID!");
                            break;
                        }

                        // Put into DB
                        Passenger p = new Passenger(name, age, gender);
                        int bookingId = 0;
                        int seatNo = 1;

                        for (int i = 0; i < seats; i++) {
                            passengerDAO.addPassenger(p, bookingId, seatNo);
                            seatNo++; // next seat
                        }
                        int passengerId = passengerDAO.addPassenger(p, bookingId, seatNo);

                        bookingDAO.bookSeats(trainId, passengerId, Date.valueOf(journeyDate), seats);
                        break;

                    case 6:
                        System.out.print("Enter Train ID: ");
                        int id = sc.nextInt();
                        trainDAO.getTotalSeats(id);
                        break;
                    case 7:
                        System.out.print("Enter Booking ID to cancel: ");
                        bookingId = sc.nextInt();

                        bookingDAO.cancelBooking(bookingId);
                        break;

                    case 8:
                        bookingDAO.viewBookingHistory();
                        break;

                    case 9:
                        sc.nextLine(); // clear buffer

                        System.out.print("Enter Source: ");
                        String source = sc.nextLine();

                        System.out.print("Enter Destination: ");
                        String destination = sc.nextLine();

                        trainDAO.searchTrain(source, destination);
                        break;

                    case 10: {
                        List<Route> routes = routeDAO.getAllRoutes();

                        Map<Integer, String> stationMap = dao.getStationIdNameMap();

                        System.out.println("-----------------------------------");
                        System.out.println("Total routes fetched: " + routes.size());
                        System.out.println("-----------------------------------");

                        Map<Integer, List<Route>> graph = GraphBuilder.buildGraph(routes);

                        for (Integer stationId : graph.keySet()) {
                            String stationName = stationMap.get(stationId);
                            System.out.println("From " + stationName + ":");

                            for (Route r : graph.get(stationId)) {

                                String destName = stationMap.get(r.getDestinationStationId());

                                System.out.println("  -> " + destName +
                                        " (" + r.getDistanceKm() + " km)");
                            }
                            System.out.println("-----------------------------------");
                        }
                        break;
                    }

                    case 11: {
                        List<Route> routes = routeDAO.getAllRoutes();
                        Map<Integer, List<Route>> graph = GraphBuilder.buildGraph(routes);
                        Map<Integer, String> stationMap = dao.getStationIdNameMap();

                        sc.nextLine(); // clear buffer

                        System.out.print("Enter Source Station Name: ");
                        String sourceName = sc.nextLine();

                        System.out.print("Enter Destination Station Name: ");
                        String destName = sc.nextLine();

                        int sourceId = dao.getStationIdByName(sourceName);
                        int destId = dao.getStationIdByName(destName);

                        if (sourceId == -1 || destId == -1) {
                            System.out.println("Invalid station name!");
                            break;
                        }

                        ShortestPathService.findShortestPath(graph, sourceId, destId, stationMap);

                        break;
                    }

                    case 12:
                        System.out.println("Exiting...");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
