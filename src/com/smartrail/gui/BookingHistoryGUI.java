package com.smartrail.gui;

import com.smartrail.util.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;

public class BookingHistoryGUI extends JFrame {

    private static final Color PRIMARY      = new Color(72, 52, 120);
    private static final Color PRIMARY_DARK = new Color(50, 35, 90);
    private static final Color ACCENT       = new Color(155, 89, 182);
    private static final Color WHITE        = Color.WHITE;
    private static final Color TEXT_GREY    = new Color(100, 100, 100);
    private static final Color BG_LIGHT     = new Color(245, 240, 255);
    private static final Color DIVIDER      = new Color(220, 210, 235);
    private static final Color GREEN        = new Color(40, 160, 80);
    private static final Color ERROR_RED    = new Color(200, 50, 50);

    private final int userId;
    private JPanel listPanel;
    private JScrollPane scrollPane;

    public BookingHistoryGUI(int userId) {
        this.userId = userId;

        setTitle("RailMatrix — My Bookings");
        setSize(860, 620);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildHeader(), BorderLayout.NORTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(BG_LIGHT);
        listPanel.setBorder(new EmptyBorder(16, 24, 24, 24));

        scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getViewport().setBackground(BG_LIGHT);
        add(scrollPane, BorderLayout.CENTER);

        loadBookings();
        setVisible(true);
    }

    // ────────────────────────────────────────────────────────────
    // HEADER
    // ────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_DARK);
        header.setBorder(new EmptyBorder(14, 24, 14, 24));

        JButton backBtn = navBtn("← Back");
        backBtn.addActionListener(e -> dispose());
        header.add(backBtn, BorderLayout.WEST);

        JLabel title = new JLabel("My Bookings", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(WHITE);
        header.add(title, BorderLayout.CENTER);

        JButton refreshBtn = navBtn("↻ Refresh");
        refreshBtn.addActionListener(e -> loadBookings());
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 8));
        right.setOpaque(false);
        right.add(refreshBtn);
        header.add(right, BorderLayout.EAST);

        return header;
    }

    // ────────────────────────────────────────────────────────────
    // LOAD BOOKINGS
    // ────────────────────────────────────────────────────────────
    private void loadBookings() {
        listPanel.removeAll();

        try {
            Connection con = DBConnection.getConnection();

            String query = """
                SELECT b.booking_id, b.journey_date, b.total_passengers,
                       b.booking_time,
                       t.train_name, t.train_type, t.departure, t.destination,
                       t.base_fare, t.fare_per_km,
                       COALESCE(
                           (SELECT SUM(r.distance_km)
                            FROM routes r
                            JOIN stations sd ON sd.station_id = r.departure_station_id
                                AND LOWER(sd.station_name) = LOWER(t.departure)
                            WHERE r.train_id = t.train_id),
                           0
                       ) AS total_km
                FROM bookings b
                JOIN trains t ON t.train_id = b.train_id
                WHERE b.user_id = ?
                ORDER BY b.booking_time DESC
            """;

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                found = true;
                int    bookingId   = rs.getInt("booking_id");
                String trainName   = rs.getString("train_name");
                String trainType   = rs.getString("train_type");
                String dep         = rs.getString("departure");
                String dest        = rs.getString("destination");
                Date   journeyDate = rs.getDate("journey_date");
                int    pax         = rs.getInt("total_passengers");
                double baseFare    = rs.getDouble("base_fare");
                double farePerKm   = rs.getDouble("fare_per_km");
                int    km          = rs.getInt("total_km");
                Timestamp bookedAt = rs.getTimestamp("booking_time");

                double totalFare = (baseFare + farePerKm * km) * pax;

                listPanel.add(buildBookingCard(
                        bookingId, trainName, trainType,
                        dep, dest, journeyDate,
                        pax, totalFare, bookedAt, con
                ));
                listPanel.add(Box.createVerticalStrut(12));
            }

            if (!found) {
                listPanel.add(Box.createVerticalStrut(80));
                JLabel empty = new JLabel("No bookings found.", SwingConstants.CENTER);
                empty.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                empty.setForeground(TEXT_GREY);
                empty.setAlignmentX(Component.CENTER_ALIGNMENT);
                listPanel.add(empty);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JLabel err = new JLabel("Error loading bookings.", SwingConstants.CENTER);
            err.setForeground(ERROR_RED);
            err.setAlignmentX(Component.CENTER_ALIGNMENT);
            listPanel.add(err);
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    // ────────────────────────────────────────────────────────────
    // BOOKING CARD
    // ────────────────────────────────────────────────────────────
    private JPanel buildBookingCard(int bookingId, String trainName, String trainType,
                                    String dep, String dest, Date journeyDate,
                                    int pax, double totalFare, Timestamp bookedAt,
                                    Connection con) {

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // ── Main card ────────────────────────────────────────────
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
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(0, 0, 0, 16);

        // Train info
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        namePanel.setOpaque(false);
        JLabel nameLabel = new JLabel(trainName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        nameLabel.setForeground(PRIMARY_DARK);
        namePanel.add(nameLabel);
        namePanel.add(typeBadge(trainType));

        JLabel bookIdLabel = new JLabel("Booking #" + bookingId
                + "  •  Booked: " + bookedAt.toString().substring(0, 16));
        bookIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        bookIdLabel.setForeground(TEXT_GREY);

        JPanel nameCol = new JPanel();
        nameCol.setLayout(new BoxLayout(nameCol, BoxLayout.Y_AXIS));
        nameCol.setOpaque(false);
        nameCol.add(namePanel);
        nameCol.add(bookIdLabel);

        gc.gridx = 0; gc.weightx = 2.5;
        card.add(nameCol, gc);

        // Route
        gc.gridx = 1; gc.weightx = 1.5; gc.insets = new Insets(0, 8, 0, 8);
        card.add(infoCol("Route", dep + " → " + dest), gc);

        // Journey date
        gc.gridx = 2; gc.weightx = 1;
        card.add(infoCol("Journey Date", journeyDate.toString()), gc);

        // Passengers
        gc.gridx = 3; gc.weightx = 0.8;
        card.add(infoCol("Passengers", String.valueOf(pax)), gc);

        // Total fare
        gc.gridx = 4; gc.weightx = 0.8;
        JPanel fareCol = infoCol("Total Fare", "₹" + String.format("%.0f", totalFare));
        ((JLabel)((JPanel)fareCol).getComponent(1)).setForeground(PRIMARY);
        card.add(fareCol, gc);

        // Status badge
        boolean upcoming = journeyDate.after(new java.util.Date());
        JLabel statusBadge = statusBadge(upcoming ? "Upcoming" : "Completed", upcoming);
        JPanel statusCol = new JPanel();
        statusCol.setLayout(new BoxLayout(statusCol, BoxLayout.Y_AXIS));
        statusCol.setOpaque(false);
        JLabel statusLbl = new JLabel("Status");
        statusLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusLbl.setForeground(TEXT_GREY);
        statusLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusBadge.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusCol.add(statusLbl);
        statusCol.add(Box.createVerticalStrut(4));
        statusCol.add(statusBadge);

        gc.gridx = 5; gc.weightx = 0.8;
        card.add(statusCol, gc);

        // Expand passengers button
        JButton detailsBtn = new JButton("▾ Passengers") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(230, 220, 255));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.setColor(PRIMARY);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        detailsBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        detailsBtn.setPreferredSize(new Dimension(110, 32));
        detailsBtn.setContentAreaFilled(false);
        detailsBtn.setBorderPainted(false);
        detailsBtn.setFocusPainted(false);
        detailsBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Passenger details panel (hidden)
        JPanel paxPanel = buildPassengerPanel(bookingId, con);
        paxPanel.setVisible(false);

        detailsBtn.addActionListener(e -> {
            boolean showing = paxPanel.isVisible();
            paxPanel.setVisible(!showing);
            detailsBtn.setText(showing ? "▾ Passengers" : "▴ Passengers");
            wrapper.revalidate();
            wrapper.repaint();
            scrollPane.revalidate();
        });

        gc.gridx = 6; gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(0, 8, 0, 0);
        card.add(detailsBtn, gc);

        wrapper.add(card);
        wrapper.add(paxPanel);
        return wrapper;
    }

    // ────────────────────────────────────────────────────────────
    // PASSENGER DETAIL PANEL
    // ────────────────────────────────────────────────────────────
    private JPanel buildPassengerPanel(int bookingId, Connection con) {
        JPanel panel = new JPanel() {
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
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(12, 20, 12, 20));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(2, 8, 2, 8);
        gc.gridy = 0; gc.gridx = 0;

        // Header row
        for (String col : new String[]{"#", "Name", "Age", "Gender", "Seat"}) {
            JLabel hdr = new JLabel(col);
            hdr.setFont(new Font("Segoe UI", Font.BOLD, 12));
            hdr.setForeground(PRIMARY);
            panel.add(hdr, gc);
            gc.gridx++;
        }

        try {
            String query = "SELECT * FROM passengers WHERE booking_id = ? ORDER BY seat_number";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, bookingId);
            ResultSet rs = ps.executeQuery();

            int row = 1;
            while (rs.next()) {
                gc.gridy = row; gc.gridx = 0;
                addCell(panel, gc, String.valueOf(row));
                gc.gridx++; addCell(panel, gc, rs.getString("passenger_name"));
                gc.gridx++; addCell(panel, gc, String.valueOf(rs.getInt("age")));
                gc.gridx++; addCell(panel, gc, rs.getString("gender"));
                gc.gridx++; addCell(panel, gc, "Seat " + rs.getInt("seat_number"));
                row++;
            }

            if (row == 1) {
                gc.gridy = 1; gc.gridx = 0; gc.gridwidth = 5;
                JLabel none = new JLabel("No passenger details found.");
                none.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                none.setForeground(TEXT_GREY);
                panel.add(none, gc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return panel;
    }

    private void addCell(JPanel panel, GridBagConstraints gc, String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(new Color(50, 40, 80));
        panel.add(lbl, gc);
    }

    // ────────────────────────────────────────────────────────────
    // UI HELPERS
    // ────────────────────────────────────────────────────────────
    private JPanel infoCol(String label, String value) {
        JPanel col = new JPanel();
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(TEXT_GREY);
        JLabel val = new JLabel(value);
        val.setFont(new Font("Segoe UI", Font.BOLD, 13));
        val.setForeground(PRIMARY_DARK);
        col.add(lbl);
        col.add(val);
        return col;
    }

    private JLabel typeBadge(String text) {
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

    private JLabel statusBadge(String text, boolean upcoming) {
        JLabel badge = new JLabel(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(upcoming ? new Color(220, 245, 228) : new Color(230, 230, 230));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(upcoming ? GREEN : TEXT_GREY);
        badge.setBorder(new EmptyBorder(3, 10, 3, 10));
        badge.setOpaque(false);
        return badge;
    }

    private JButton navBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(WHITE);
        btn.setBackground(PRIMARY_DARK);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setForeground(ACCENT); }
            public void mouseExited(MouseEvent e)  { btn.setForeground(WHITE);  }
        });
        return btn;
    }
}
