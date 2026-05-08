package com.smartrail.gui;

import javax.swing.*;
import java.awt.*;

public class UserDashboard extends JFrame {

    private int currentUserId;

    public UserDashboard(int currentUserId) {

        this.currentUserId = currentUserId;

        setTitle("RailMatrix Dashboard");

        setSize(500, 400);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new GridLayout(5, 1, 10, 10));

        JButton searchBtn = new JButton("Search Train");

        JButton bookBtn = new JButton("Book Ticket");

        JButton cancelBtn = new JButton("Cancel Ticket");

        JButton historyBtn = new JButton("View Booking History");

        JButton logoutBtn = new JButton("Logout");

        add(searchBtn);

        add(bookBtn);

        add(cancelBtn);

        add(historyBtn);

        add(logoutBtn);

        // Button Actions

        searchBtn.addActionListener(e -> {

            try {
                new SearchTrainGUI();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        bookBtn.addActionListener(e -> {

            JOptionPane.showMessageDialog(this,
                    "Booking Feature Coming Next");
        });

        cancelBtn.addActionListener(e -> {

            JOptionPane.showMessageDialog(this,
                    "Cancel Feature Coming Next");
        });

        historyBtn.addActionListener(e -> {

            JOptionPane.showMessageDialog(this,
                    "History Feature Coming Next");
        });

        logoutBtn.addActionListener(e -> {

            dispose();

            new LoginGUI();
        });

        setVisible(true);
    }
}