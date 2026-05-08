package com.smartrail.gui;

import com.smartrail.dao.RouteDAO;
import com.smartrail.service.RouteService;
import com.smartrail.util.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.List;

public class SearchTrainGUI extends JFrame {

    public SearchTrainGUI() throws Exception {

        RouteDAO routeDAO = new RouteDAO(DBConnection.getConnection());

        setTitle("Search Train Route");

        setSize(500, 400);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new FlowLayout());

        JLabel sourceLabel = new JLabel("Source Station ID:");

        JTextField sourceField = new JTextField(15);

        JLabel destLabel = new JLabel("Destination Station ID:");

        JTextField destField = new JTextField(15);

        JButton searchBtn = new JButton("Search Route");

        JTextArea resultArea = new JTextArea(10, 35);

        resultArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(sourceLabel);

        add(sourceField);

        add(destLabel);

        add(destField);

        add(searchBtn);

        add(scrollPane);

        searchBtn.addActionListener(e -> {

            try {

                int sourceId =
                        Integer.parseInt(sourceField.getText());

                int destId =
                        Integer.parseInt(destField.getText());

                RouteService routeService =
                        new RouteService(routeDAO);

                List<Integer> path =
                        routeService.getShortestPath(sourceId, destId);

                if (path == null || path.isEmpty()) {

                    resultArea.setText("No Route Found!");

                } else {

                    StringBuilder sb = new StringBuilder();

                    sb.append("Shortest Route:\n\n");

                    for (Integer station : path) {

                        sb.append(station).append(" → ");
                    }

                    sb.delete(sb.length() - 3, sb.length());

                    resultArea.setText(sb.toString());
                }

            } catch (Exception ex) {

                resultArea.setText("Invalid Input!");
            }
        });

        setVisible(true);
    }
}