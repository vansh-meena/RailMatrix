package com.smartrail.gui;

import com.smartrail.dao.UserDAO;
import com.smartrail.main.UserMenu;
import com.smartrail.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginGUI extends JFrame{

    public LoginGUI() {
        JFrame frame = new JFrame("User Login");

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();

        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();

        JButton loginBtn = new JButton("Login");

        JButton backBtn = new JButton("Back");

        loginBtn.addActionListener(e -> {

            String email = userField.getText();
            String password = new String(passField.getPassword());

            UserDAO userDAO = new UserDAO();

            try {

                if (!userDAO.isUserExists(email)) {

                    JOptionPane.showMessageDialog(frame,
                            "User not found!");

                    return;
                }

                User user = userDAO.login(email, password);

                if (user == null) {

                    JOptionPane.showMessageDialog(frame,
                            "Invalid Password!");

                } else {

                    JOptionPane.showMessageDialog(frame,
                            "Login Successful!");

                    frame.dispose();

                    int currentUserId = user.getUserId();

                    new UserDashboard(currentUserId);
                }

            } catch (Exception ex) {

                ex.printStackTrace();

                JOptionPane.showMessageDialog(frame,
                        "Database Error!");
            }
        });

        frame.setLayout(new GridLayout(4, 2));
        frame.add(userLabel);
        frame.add(userField);
        frame.add(passLabel);
        frame.add(passField);
        frame.add(loginBtn);
        frame.add(backBtn);

        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        backBtn.addActionListener(e -> {

            dispose();

            new RoleSelectionGUI();
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
