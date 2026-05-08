package com.smartrail.main;

import com.smartrail.dao.*;
import com.smartrail.model.Route;
import com.smartrail.service.GraphBuilder;
import com.smartrail.service.ShortestPathService;
import com.smartrail.util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdminMenu {

    public void start() throws Exception {

        Connection con = DBConnection.getConnection();
        StationDAO dao = new StationDAO(con);
        TrainDAO trainDAO = new TrainDAO(con);
        BookingDAO bookingDAO = new BookingDAO(con);
        PassengerDAO passengerDAO = new PassengerDAO(con);
        RouteDAO routeDAO = new RouteDAO(con);
        UserDAO userDAO = new UserDAO();
        AdminDAO adminDAO = new AdminDAO();
        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== ADMIN PANEL =====");
            System.out.println("1. Add Station");
            System.out.println("2. View Stations");
            System.out.println("3. Add Train");
            System.out.println("4. View Trains");
            System.out.println("5. Book Ticket");
            System.out.println("6. Check Available Seats");
            System.out.println("7. Cancel Booking");
            System.out.println("8. View Booking History");
            System.out.println("9. Search Train by Route");
            System.out.println("10. View Train Routes");
            System.out.println("11. Find Shortest Route");
            System.out.println("12. Logout");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1: {
                    System.out.print("Enter station name: ");
                    String stationName = sc.nextLine();

                    System.out.print("Enter city: ");
                    String city = sc.nextLine();

                    try {
                        String query = "INSERT INTO stations(station_name, city) VALUES (?, ?)";
                        PreparedStatement ps = con.prepareStatement(query);

                        ps.setString(1, stationName);
                        ps.setString(2, city);

                        ps.executeUpdate();

                        System.out.println("Station added successfully!");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }

                case 2:
                    try {
                        String query = "SELECT * FROM stations";
                        PreparedStatement ps = con.prepareStatement(query);

                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {
                            System.out.println(
                                    rs.getInt("station_id") + " | " +
                                            rs.getString("station_name") + " | " +
                                            rs.getString("city")
                            );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:

                    System.out.print("Enter train name: ");
                    String trainName = sc.nextLine();

                    System.out.print("Enter departure: ");
                    String dep = sc.nextLine();

                    System.out.print("Enter destination: ");
                    String dest = sc.nextLine();

                    System.out.print("Enter total seats: ");
                    int totalSeats = sc.nextInt();
                    sc.nextLine();

                    try {
                        String query = "INSERT INTO trains(train_name, departure, destination, total_seats) VALUES (?, ?, ?, ?)";
                        PreparedStatement ps = con.prepareStatement(query);

                        ps.setString(1, trainName);
                        ps.setString(2, dep);
                        ps.setString(3, dest);
                        ps.setInt(4, totalSeats);

                        ps.executeUpdate();

                        System.out.println("Train added successfully!");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    try {
                        String query = "SELECT * FROM trains";
                        PreparedStatement ps = con.prepareStatement(query);

                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {
                            System.out.println(
                                    rs.getInt("train_id") + " | " +
                                            rs.getString("train_name") + " | " +
                                            rs.getString("departure") + " → " +
                                            rs.getString("destination") + " | Seats: " +
                                            rs.getInt("total_seats")
                            );
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 5:
                    System.out.println("Redirecting to booking...");

                    UserMenu userMenu = new UserMenu(0); // admin booking
                    userMenu.start();
                    break;

                case 6:

                    System.out.print("Enter Train ID: ");
                    int trainId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter date (YYYY-MM-DD): ");
                    Date date = Date.valueOf(sc.nextLine());

                    int seats = trainDAO.getAvailableSeats(trainId, date);

                    System.out.println("Available Seats: " + seats);
                    break;

                case 7:
                    System.out.println("Cancellation feature here...");
                    break;

                case 8:
                    try {
                        String query = "SELECT * FROM bookings";
                        PreparedStatement ps = con.prepareStatement(query);

                        ResultSet rs = ps.executeQuery();

                        while (rs.next()) {
                            System.out.println(
                                    "Booking ID: " + rs.getInt("booking_id") +
                                            " | Train ID: " + rs.getInt("train_id") +
                                            " | User ID: " + rs.getInt("user_id") +
                                            " | Seats: " + rs.getInt("seats_booked")
                            );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case 9:

                    System.out.print("Enter source: ");
                    String src = sc.nextLine();

                    System.out.print("Enter destination: ");
                    String dst = sc.nextLine();

                    trainDAO.searchTrain(src, dst);
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

                    int sourceId = Integer.parseInt(dao.getStationName(sourceName));
                    int destId = Integer.parseInt(dao.getStationName(destName));

                    if (sourceId == -1 || destId == -1) {
                        System.out.println("Invalid station name!");
                        break;
                    }

                    ShortestPathService.findShortestPath(graph, sourceId, destId);

                    break;
                }
                case 12:
                    System.out.println("Admin logged out!");
                    return;

                default:
                    System.out.println("Invalid Choice!");
                    break;
            }
        }
    }
}
