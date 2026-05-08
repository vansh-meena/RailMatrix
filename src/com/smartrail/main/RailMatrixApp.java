package com.smartrail.main;

import java.sql.Date;
import java.util.*;

import com.smartrail.dao.*;
import com.smartrail.model.*;
import com.smartrail.dao.RouteDAO;
import com.smartrail.util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class RailMatrixApp {
    public static void main(String[] args) throws SQLException {
        int currentUserId = -1;
        try {
            Connection con = DBConnection.getConnection();
//            StationDAO dao = new StationDAO(con);
//            TrainDAO trainDAO = new TrainDAO(con);
//            BookingDAO bookingDAO = new BookingDAO(con);
//            PassengerDAO passengerDAO = new PassengerDAO(con);
//            RouteDAO routeDAO = new RouteDAO(con);
//            UserDAO userDAO = new UserDAO();
//            AdminDAO adminDAO = new AdminDAO();


            Scanner sc = new Scanner(System.in);

            UserDAO userDAO = new UserDAO();
            boolean isAdmin = false;

            while (true) {

                System.out.println("\n===== MAIN MENU =====");
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    // ================= REGISTER =================
                    case 1:
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter email: ");
                        String email = sc.nextLine();

                        System.out.print("Enter password: ");
                        String password = sc.nextLine();

                        User user = new User();
                        user.setName(name);
                        user.setEmail(email);
                        user.setPassword(password);

                        if (userDAO.register(user)) {
                            System.out.println("Registration successful!");
                        } else {
                            System.out.println("Registration failed!");
                        }
                        break;

                    // ================= LOGIN =================
                    case 2:

                        System.out.println("1. User Login");
                        System.out.println("2. Admin Login");

                        int loginChoice = sc.nextInt();
                        sc.nextLine();

                        // -------- USER LOGIN --------
                        if (loginChoice == 1) {

                            while (true) {

                                System.out.print("Enter email: ");
                                String uEmail = sc.nextLine();

                                if (!userDAO.isUserExists(uEmail)) {
                                    System.out.println("User not found! Try again.\n");
                                    continue;
                                }

                                System.out.print("Enter password: ");
                                String uPass = sc.nextLine();

                                User u = userDAO.login(uEmail, uPass);

                                if (u == null) {
                                    System.out.println("Invalid Password! Try again.\n");
                                } else {
                                    currentUserId = u.getUserId();
                                    System.out.println("Login successful! Welcome " + u.getName());

                                    // 👉 Go to User Menu
                                    UserMenu userMenu = new UserMenu(currentUserId);
                                    userMenu.start();

                                    break;
                                }
                            }
                        }

                        // -------- ADMIN LOGIN --------
                        else if (loginChoice == 2) {

                            int attempts = 0;
                            long blockTime = 0;

                            while (true) {

                                if (System.currentTimeMillis() < blockTime) {
                                    System.out.println("Admin login disabled. Try later.");
                                    break;
                                }

                                System.out.print("Enter admin email: ");
                                String aEmail = sc.nextLine();

                                System.out.print("Enter password: ");
                                String aPass = sc.nextLine();

                                if (AdminDAO.adminLogin(aEmail, aPass)) {
                                    isAdmin = true;
                                    System.out.println("Admin login successful!");

                                    // 👉 Go to Admin Menu
                                    AdminMenu adminMenu = new AdminMenu();
                                    adminMenu.start();

                                    break;
                                } else {
                                    System.out.println("Invalid credentials! Try again.\n");
                                    attempts++;
                                }

                                if (attempts == 10) {
                                    blockTime = System.currentTimeMillis() + (10 * 60 * 1000);
                                    System.out.println("Too many attempts! Blocked for 10 minutes.");
                                    break;
                                }
                            }
                        }

                        break;

                    case 3:
                        System.out.println("Exiting...");
                        return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
