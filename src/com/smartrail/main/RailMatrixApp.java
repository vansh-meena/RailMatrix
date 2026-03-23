package com.smartrail.main;

import java.util.*;

import com.smartrail.dao.BookingDAO;
import com.smartrail.dao.PassengerDAO;
import com.smartrail.dao.StationDAO;
import com.smartrail.dao.TrainDAO;
import com.smartrail.model.Passenger;
import com.smartrail.model.Station;
import com.smartrail.model.Trains;
import com.smartrail.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class RailMatrixApp {
    public static void main(String[] args) throws SQLException {
        try {
            Connection con = DBConnection.getConnection();
            StationDAO dao = new StationDAO(con);
            TrainDAO trainDAO = new TrainDAO(con);
            BookingDAO bookingDAO = new BookingDAO(con);
            PassengerDAO passengerDAO = new PassengerDAO(con);

            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("\n===== RailMatrix Menu =====");
                System.out.println("1. Add Station");
                System.out.println("2. View All Stations");
                System.out.println("3. Add Train");
                System.out.println("4. View Trains");
                System.out.println("5. Book Seats");
                System.out.println("6. Check Available Seats");
                System.out.println("7. Cancel Booking");
                System.out.println("8. View Booking History");
                System.out.println("9. Exit");
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
                        System.out.print("Enter Train Name: ");
                        String tname = sc.nextLine();

                        System.out.print("Enter Departure: ");
                        String dep = sc.nextLine();

                        System.out.print("Enter Destination: ");
                        String dest = sc.nextLine();

                        System.out.print("Enter Total Seats: ");
                        int seats = sc.nextInt();
                        sc.nextLine();

                        Trains t = new Trains(tname, dep, dest, seats);
                        trainDAO.addTrain(t);
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

                        // Create passenger
                        Passenger p = new Passenger(name, age, gender);
                        int passengerId = passengerDAO.addPassenger(p);

                        // Now booking
                        System.out.print("Enter Train ID: ");
                        int trainId = sc.nextInt();

                        System.out.print("Enter number of seats: ");
                        int seats = sc.nextInt();

                        bookingDAO.bookSeats(trainId, passengerId, seats);
                        break;

                    case 6:
                        System.out.print("Enter Train ID: ");
                        int id = sc.nextInt();
                        trainDAO.getAvailableSeats(id);
                        break;
                    case 7:
                        System.out.print("Enter Booking ID to cancel: ");
                        int bookingId = sc.nextInt();

                        bookingDAO.cancelBooking(bookingId);
                        break;

                    case 8:
                        bookingDAO.viewBookingHistory();
                        break;

                    case 9:
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
