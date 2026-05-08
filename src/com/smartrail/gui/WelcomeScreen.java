package com.smartrail.gui;

import javax.swing.*;
import java.awt.*;

public class WelcomeScreen extends JFrame {

    public WelcomeScreen() {

        setTitle("RailMatrix System");

        setSize(400, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new GridLayout(3, 1, 15, 15));

        JButton registerBtn = new JButton("Register");

        JButton loginBtn = new JButton("Login");

        JButton exitBtn = new JButton("Exit");

        add(registerBtn);

        add(loginBtn);

        add(exitBtn);

        // Register Button
        registerBtn.addActionListener(e -> {

            JOptionPane.showMessageDialog(this,
                    "Register Screen Coming Next");
        });

        // Login Button
        loginBtn.addActionListener(e -> {

            dispose();

            new RoleSelectionGUI();
        });

        // Exit Button
        exitBtn.addActionListener(e -> {

            System.exit(0);
        });

        setVisible(true);
    }

    public static void main(String[] args) {

        new WelcomeScreen();
    }
}
