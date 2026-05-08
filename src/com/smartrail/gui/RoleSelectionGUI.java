package com.smartrail.gui;

import javax.swing.*;
import java.awt.*;

public class RoleSelectionGUI extends JFrame {

    public RoleSelectionGUI() {

        setTitle("Select Role");

        setSize(400, 250);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new GridLayout(3, 1, 15, 15));

        JButton userBtn = new JButton("Login as User");

        JButton adminBtn = new JButton("Login as Admin");

        JButton backBtn = new JButton("Back");

        add(userBtn);

        add(adminBtn);

        add(backBtn);

        userBtn.addActionListener(e -> {

            dispose();

            new LoginGUI();
        });

        adminBtn.addActionListener(e -> {

            JOptionPane.showMessageDialog(this,
                    "Admin Login Coming Next");
        });

        backBtn.addActionListener(e -> {

            dispose();

            new WelcomeScreen();
        });

        setVisible(true);
    }
}
