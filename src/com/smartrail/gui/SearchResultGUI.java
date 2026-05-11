package com.smartrail.gui;

import com.smartrail.dao.BookingDAO;
import com.smartrail.dao.PassengerDAO;
import com.smartrail.dao.TrainDAO;
import com.smartrail.model.Passenger;
import com.smartrail.util.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchResultGUI extends JFrame {

    private static final Color PRIMARY      = new Color(72, 52, 120);
    private static final Color PRIMARY_DARK = new Color(50, 35, 90);
    private static final Color ACCENT       = new Color(155, 89, 182);
    private static final Color WHITE        = Color.WHITE;
    private static final Color TEXT_GREY    = new Color(100, 100, 100);
    private static final Color BG_LIGHT     = new Color(245, 240, 255);
    private static final Color CARD_BG      = new Color(252, 250, 255);
    private static final Color DIVIDER      = new Color(220, 210, 235);
    private static final Color GREEN        = new Color(40, 160, 80);
    private static final Color ERROR_RED    = new Color(200, 50, 50);

    private final String from;
    private final String to;
    private final Date journeyDate;
    private int loggedInUserId = -1; // -1 = not logged in

    private JPanel resultsPanel;
    private JScrollPane scrollPane;

    public SearchResultGUI(String from, String to, Date journeyDate) {
        this(from, to, journeyDate, -1);
    }

    public SearchResultGUI(String from, String to, Date journeyDate, int userId) {
        this.from         = from;
        this.to           = to;
        this.journeyDate  = journeyDate;
        this.loggedInUserId = userId;

        setTitle("RailMatrix — Search Results");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBackground(BG_LIGHT);
        resultsPanel.setBorder(new EmptyBorder(16, 24, 24, 24));

        scrollPane = new JScrollPane(resultsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(BG_LIGHT);
        add(scrollPane, BorderLayout.CENTER);

        loadResults();
        setVisible(true);
    }

    // ────────────────────────────────────────────────────────────
    // HEADER
    // ────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_DARK);
        header.setBorder(new EmptyBorder(14, 24, 14, 24));

        // Back button
        JButton backBtn = new JButton("← Back");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        backBtn.setForeground(WHITE);
        backBtn.setBackground(PRIMARY_DARK);
        backBtn.setBorderPainted(false);
        backBtn.setFocusPainted(false);
        backBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { backBtn.setForeground(ACCENT); }
            public void mouseExited(MouseEvent e)  { backBtn.setForeground(WHITE);  }
        });
        backBtn.addActionListener(e -> dispose());
        header.add(backBtn, BorderLayout.WEST);

        // Route info (center)
        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0;

        JLabel routeLabel = new JLabel(from + "  →  " + to, SwingConstants.CENTER);
        routeLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        routeLabel.setForeground(WHITE);
        center.add(routeLabel, gc);

        gc.gridy = 1;
        JLabel dateLabel = new JLabel(journeyDate.toString(), SwingConstants.CENTER);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateLabel.setForeground(new Color(200, 185, 220));
        center.add(dateLabel, gc);

        header.add(center, BorderLayout.CENTER);
        return header;
    }

    // ────────────────────────────────────────────────────────────
    // LOAD RESULTS FROM DB
    // ────────────────────────────────────────────────────────────
    private void loadResults() {
        resultsPanel.removeAll();

        try {
            Connection con = DBConnection.getConnection();

            // Find trains that have a route segment matching from->to (direct or via stops)
            String query = """
                SELECT DISTINCT t.train_id, t.train_name, t.train_type,
                       t.base_fare, t.fare_per_km,
                       r_dep.departure_time AS dep_time,
                       r_arr.arrival_time   AS arr_time,
                       (SELECT SUM(r2.distance_km)
                        FROM routes r2
                        WHERE r2.train_id = t.train_id
                          AND r2.stop_number BETWEEN r_dep.stop_number AND r_arr.stop_number
                       ) AS total_km,
                       COALESCE(ts.available_seats, 0) AS available_seats
                FROM trains t
                JOIN routes r_dep ON r_dep.train_id = t.train_id
                JOIN stations s_dep ON s_dep.station_id = r_dep.departure_station_id
                    AND LOWER(s_dep.station_name) = LOWER(?)
                JOIN routes r_arr ON r_arr.train_id = t.train_id
                JOIN stations s_arr ON s_arr.station_id = r_arr.destination_station_id
                    AND LOWER(s_arr.station_name) = LOWER(?)
                AND r_arr.stop_number >= r_dep.stop_number
                LEFT JOIN train_schedule ts ON ts.train_id = t.train_id
                    AND ts.journey_date = ?
                WHERE t.departure = ? OR r_dep.departure_station_id IS NOT NULL
                ORDER BY dep_time
            """;

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, from);
            ps.setString(2, to);
            ps.setDate(3, journeyDate);
            ps.setString(4, from);

            ResultSet rs = ps.executeQuery();
            boolean found = false;

            while (rs.next()) {
                found = true;
                int    trainId    = rs.getInt("train_id");
                String trainName  = rs.getString("train_name");
                String trainType  = rs.getString("train_type");
                Time   depTime    = rs.getTime("dep_time");
                Time   arrTime    = rs.getTime("arr_time");
                int    km         = rs.getInt("total_km");
                double baseFare   = rs.getDouble("base_fare");
                double farePerKm  = rs.getDouble("fare_per_km");
                int    seats      = rs.getInt("available_seats");

                double fare = baseFare + (farePerKm * km);
                String duration = formatDuration(depTime, arrTime);

                resultsPanel.add(buildTrainCard(
                        trainId, trainName, trainType,
                        depTime, arrTime, duration,
                        km, fare, seats
                ));
                resultsPanel.add(Box.createVerticalStrut(12));
            }

            if (!found) {
                JLabel noResult = new JLabel("No trains found for this route.", SwingConstants.CENTER);
                noResult.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                noResult.setForeground(TEXT_GREY);
                noResult.setAlignmentX(Component.CENTER_ALIGNMENT);
                resultsPanel.add(Box.createVerticalStrut(80));
                resultsPanel.add(noResult);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JLabel err = new JLabel("Error loading results. Check DB connection.", SwingConstants.CENTER);
            err.setForeground(ERROR_RED);
            err.setAlignmentX(Component.CENTER_ALIGNMENT);
            resultsPanel.add(err);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    // ────────────────────────────────────────────────────────────
    // TRAIN RESULT CARD
    // ────────────────────────────────────────────────────────────
    private JPanel buildTrainCard(int trainId, String trainName, String trainType,
                                  Time depTime, Time arrTime, String duration,
                                  int km, double fare, int seats) {

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // ── Main info row ────────────────────────────────────────
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WHITE);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 16, 16));
                g2.setColor(DIVIDER);
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 16, 16));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(16, 20, 16, 20));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(0, 0, 0, 16);

        // Train name + type badge
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        namePanel.setOpaque(false);
        JLabel nameLabel = new JLabel(trainName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        nameLabel.setForeground(PRIMARY_DARK);
        JLabel typeBadge = badge(trainType);
        namePanel.add(nameLabel);
        namePanel.add(typeBadge);

        JLabel idLabel = new JLabel("Train #" + trainId + "  •  " + km + " km");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        idLabel.setForeground(TEXT_GREY);

        JPanel nameCol = new JPanel();
        nameCol.setLayout(new BoxLayout(nameCol, BoxLayout.Y_AXIS));
        nameCol.setOpaque(false);
        nameCol.add(namePanel);
        nameCol.add(idLabel);

        gc.gridx = 0; gc.gridy = 0; gc.weightx = 2;
        card.add(nameCol, gc);

        // Departure time
        gc.gridx = 1; gc.weightx = 1; gc.insets = new Insets(0, 8, 0, 8);
        card.add(timeCol("Departure", depTime != null ? depTime.toString().substring(0,5) : "--:--"), gc);

        // Duration
        gc.gridx = 2; gc.weightx = 1;
        card.add(timeCol("Duration", duration), gc);

        // Arrival time
        gc.gridx = 3; gc.weightx = 1;
        card.add(timeCol("Arrival", arrTime != null ? arrTime.toString().substring(0,5) : "--:--"), gc);

        // Fare
        gc.gridx = 4; gc.weightx = 1;
        JPanel fareCol = timeCol("Fare", "₹" + String.format("%.0f", fare));
        ((JLabel)((JPanel)fareCol.getComponent(1)).getComponent(0)).setForeground(PRIMARY);
        card.add(fareCol, gc);

        // Seats
        gc.gridx = 5; gc.weightx = 1;
        JLabel seatsVal = new JLabel(seats + " seats");
        seatsVal.setFont(new Font("Segoe UI", Font.BOLD, 14));
        seatsVal.setForeground(seats > 10 ? GREEN : seats > 0 ? new Color(200, 130, 0) : ERROR_RED);
        JPanel seatsCol = labeledCol("Available", seatsVal);
        card.add(seatsCol, gc);

        // Book button
        JButton bookBtn = new JButton(seats > 0 ? "Book" : "Full") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = seats > 0
                        ? (getModel().isRollover() ? PRIMARY_DARK : PRIMARY)
                        : new Color(180, 180, 180);
                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 14, 14));
                g2.setColor(WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        bookBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        bookBtn.setPreferredSize(new Dimension(80, 36));
        bookBtn.setContentAreaFilled(false);
        bookBtn.setBorderPainted(false);
        bookBtn.setFocusPainted(false);
        bookBtn.setEnabled(seats > 0);
        bookBtn.setCursor(seats > 0
                ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                : Cursor.getDefaultCursor());

        // ── Booking form panel (hidden initially) ───────────────
        JPanel bookingForm = buildBookingForm(trainId, trainName, fare, seats, wrapper, bookBtn);
        bookingForm.setVisible(false);

        bookBtn.addActionListener(e -> {
            if (loggedInUserId == -1) {
                int choice = JOptionPane.showConfirmDialog(this,
                        "You need to login to book a ticket.\nGo to Login?",
                        "Login Required", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) { dispose(); new LoginGUI(); }
            } else {
                boolean showing = bookingForm.isVisible();
                bookingForm.setVisible(!showing);
                bookBtn.setText(!showing ? "Close" : "Book");
                wrapper.revalidate();
                wrapper.repaint();
                scrollPane.revalidate();
                if (!showing) SwingUtilities.invokeLater(() ->
                        scrollPane.getVerticalScrollBar().setValue(
                                wrapper.getY() + wrapper.getHeight()));
            }
        });

        gc.gridx = 6; gc.weightx = 0; gc.insets = new Insets(0, 8, 0, 0);
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        card.add(bookBtn, gc);

        wrapper.add(card);
        wrapper.add(bookingForm);
        return wrapper;
    }

    // ────────────────────────────────────────────────────────────
    // BOOKING FORM (inline expanded)
    // ────────────────────────────────────────────────────────────
    private JPanel buildBookingForm(int trainId, String trainName,
                                    double farePerPax, int maxSeats,
                                    JPanel parentWrapper, JButton bookBtn) {

        JPanel form = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(248, 244, 255));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 0, 0));
                g2.setColor(DIVIDER);
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 0, 0));
                g2.dispose();
            }
        };
        form.setOpaque(false);
        form.setLayout(new GridBagLayout());
        form.setBorder(new EmptyBorder(16, 20, 16, 20));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(6, 8, 6, 8);

        // Passenger count
        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 1;
        form.add(formLabel("No. of Passengers"), gc);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, Math.min(maxSeats, 6), 1);
        JSpinner passengerSpinner = new JSpinner(spinnerModel);
        passengerSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passengerSpinner.setPreferredSize(new Dimension(80, 32));
        gc.gridx = 1;
        form.add(passengerSpinner, gc);

        // Total fare display
        JLabel totalFareLabel = new JLabel("Total: ₹" + String.format("%.0f", farePerPax));
        totalFareLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalFareLabel.setForeground(PRIMARY);
        gc.gridx = 2; gc.gridwidth = 2;
        form.add(totalFareLabel, gc);

        // Update fare on spinner change
        passengerSpinner.addChangeListener(e -> {
            int count = (int) passengerSpinner.getValue();
            totalFareLabel.setText("Total: ₹" + String.format("%.0f", farePerPax * count));
        });

        // Passenger rows container
        JPanel passengerRows = new JPanel();
        passengerRows.setLayout(new BoxLayout(passengerRows, BoxLayout.Y_AXIS));
        passengerRows.setOpaque(false);

        List<JTextField> nameFields   = new ArrayList<>();
        List<JTextField> ageFields    = new ArrayList<>();
        List<JComboBox<String>> genderBoxes = new ArrayList<>();

        Runnable refreshPassengerRows = () -> {
            passengerRows.removeAll();
            nameFields.clear(); ageFields.clear(); genderBoxes.clear();
            int count = (int) passengerSpinner.getValue();
            for (int i = 0; i < count; i++) {
                JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
                row.setOpaque(false);
                row.add(new JLabel("Pax " + (i+1) + ":"));

                JTextField nf = styledField("Name", 140);
                JTextField af = styledField("Age", 50);
                JComboBox<String> gf = new JComboBox<>(new String[]{"Male", "Female", "Other"});
                gf.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                gf.setPreferredSize(new Dimension(100, 30));

                nameFields.add(nf); ageFields.add(af); genderBoxes.add(gf);
                row.add(nf); row.add(af); row.add(gf);
                passengerRows.add(row);
            }
            passengerRows.revalidate();
            passengerRows.repaint();
        };

        refreshPassengerRows.run();
        passengerSpinner.addChangeListener(e -> refreshPassengerRows.run());

        gc.gridx = 0; gc.gridy = 1; gc.gridwidth = 4;
        form.add(passengerRows, gc);

        // Status label
        JLabel formStatus = new JLabel("", SwingConstants.LEFT);
        formStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gc.gridy = 2;
        form.add(formStatus, gc);

        // Confirm button
        JButton confirmBtn = new JButton("Confirm Booking") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? PRIMARY_DARK : PRIMARY);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 14, 14));
                g2.setColor(WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        confirmBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        confirmBtn.setPreferredSize(new Dimension(160, 38));
        confirmBtn.setContentAreaFilled(false);
        confirmBtn.setBorderPainted(false);
        confirmBtn.setFocusPainted(false);
        confirmBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        confirmBtn.addActionListener(e -> {
            int paxCount = (int) passengerSpinner.getValue();
            // Validate
            for (int i = 0; i < paxCount; i++) {
                if (nameFields.get(i).getText().trim().isEmpty() ||
                        nameFields.get(i).getText().equals("Name")) {
                    formStatus.setText("Please enter name for passenger " + (i+1));
                    formStatus.setForeground(ERROR_RED); return;
                }
                try { Integer.parseInt(ageFields.get(i).getText().trim()); }
                catch (NumberFormatException ex) {
                    formStatus.setText("Invalid age for passenger " + (i+1));
                    formStatus.setForeground(ERROR_RED); return;
                }
            }

            // Book
            try {
                Connection con = DBConnection.getConnection();
                TrainDAO trainDAO     = new TrainDAO(con);
                BookingDAO bookingDAO = new BookingDAO(con);
                PassengerDAO paxDAO   = new PassengerDAO(con);

                int totalSeats = trainDAO.getTotalSeats(trainId);
                trainDAO.createScheduleIfNotExists(trainId, journeyDate, totalSeats);

                int avail = trainDAO.getAvailableSeats(trainId, journeyDate);
                if (avail < paxCount) {
                    formStatus.setText("Not enough seats available.");
                    formStatus.setForeground(ERROR_RED); return;
                }

                int bookingId = bookingDAO.bookSeats(loggedInUserId, trainId, journeyDate, paxCount);
                if (bookingId == -1) {
                    formStatus.setText("Booking failed. Please try again.");
                    formStatus.setForeground(ERROR_RED); return;
                }

                for (int i = 0; i < paxCount; i++) {
                    Passenger p = new Passenger(
                            nameFields.get(i).getText().trim(),
                            Integer.parseInt(ageFields.get(i).getText().trim()),
                            (String) genderBoxes.get(i).getSelectedItem()
                    );
                    paxDAO.addPassenger(p, bookingId, i + 1);
                }

                trainDAO.updateSeats(trainId, journeyDate, paxCount);

                formStatus.setText("✓ Booking confirmed! ID: " + bookingId);
                formStatus.setForeground(GREEN);
                confirmBtn.setEnabled(false);
                bookBtn.setText("Booked");
                bookBtn.setEnabled(false);

            } catch (Exception ex) {
                ex.printStackTrace();
                formStatus.setText("Error: " + ex.getMessage());
                formStatus.setForeground(ERROR_RED);
            }
        });

        gc.gridy = 3; gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.WEST;
        form.add(confirmBtn, gc);

        return form;
    }

    // ────────────────────────────────────────────────────────────
    // UI HELPERS
    // ────────────────────────────────────────────────────────────
    private JPanel timeCol(String label, String value) {
        JLabel valLabel = new JLabel(value, SwingConstants.CENTER);
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        valLabel.setForeground(PRIMARY_DARK);
        return labeledCol(label, valLabel);
    }

    private JPanel labeledCol(String label, JLabel valLabel) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setOpaque(false);
        JLabel lbl = new JLabel(label, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(TEXT_GREY);
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        valLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        col.add(lbl);
        col.add(valLabel);
        return col;
    }

    private JLabel badge(String text) {
        JLabel badge = new JLabel(text != null ? text : "Express") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(230, 220, 255));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        badge.setForeground(PRIMARY);
        badge.setBorder(new EmptyBorder(2, 8, 2, 8));
        badge.setOpaque(false);
        return badge;
    }

    private JLabel formLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(PRIMARY);
        return lbl;
    }

    private JTextField styledField(String placeholder, int width) {
        JTextField f = new JTextField();
        f.setText(placeholder);
        f.setForeground(TEXT_GREY);
        f.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        f.setPreferredSize(new Dimension(width, 30));
        f.setBorder(BorderFactory.createCompoundBorder(
                new RegisterGUI.RoundedBorder(8, new Color(200, 180, 220)),
                BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        f.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (f.getText().equals(placeholder)) { f.setText(""); f.setForeground(Color.BLACK); }
            }
            public void focusLost(FocusEvent e) {
                if (f.getText().isEmpty()) { f.setText(placeholder); f.setForeground(TEXT_GREY); }
            }
        });
        return f;
    }

    private String formatDuration(Time dep, Time arr) {
        if (dep == null || arr == null) return "--h --m";
        long mins = (arr.getTime() - dep.getTime()) / 60000;
        if (mins < 0) mins += 24 * 60;
        return (mins / 60) + "h " + (mins % 60) + "m";
    }
}