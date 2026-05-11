package com.smartrail.gui;

import com.smartrail.dao.BookingDAO;
import com.smartrail.dao.TrainDAO;
import com.smartrail.model.Booking;
import com.smartrail.util.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;

public class CancelBookingGUI extends JFrame {

    private static final Color PRIMARY      = new Color(72, 52, 120);
    private static final Color PRIMARY_DARK = new Color(50, 35, 90);
    private static final Color ACCENT       = new Color(155, 89, 182);
    private static final Color WHITE        = Color.WHITE;
    private static final Color TEXT_GREY    = new Color(100, 100, 100);
    private static final Color BG_LIGHT     = new Color(245, 240, 255);
    private static final Color DIVIDER      = new Color(220, 210, 235);
    private static final Color GREEN        = new Color(40, 160, 80);
    private static final Color ERROR_RED    = new Color(200, 50, 50);
    private static final Color WARN_ORANGE  = new Color(200, 100, 20);

    private final int userId;
    private JPanel listPanel;
    private JScrollPane scrollPane;

    public CancelBookingGUI(int userId) {
        this.userId = userId;

        setTitle("RailMatrix — Cancel Booking");
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

        loadUpcomingBookings();
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

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0;

        JLabel title = new JLabel("Cancel Booking", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(WHITE);
        center.add(title, gc);

        gc.gridy = 1;
        JLabel sub = new JLabel("Only upcoming bookings can be cancelled", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        sub.setForeground(new Color(200, 185, 220));
        center.add(sub, gc);

        header.add(center, BorderLayout.CENTER);
        return header;
    }

    // ────────────────────────────────────────────────────────────
    // LOAD UPCOMING BOOKINGS
    // ────────────────────────────────────────────────────────────
    private void loadUpcomingBookings() {
        listPanel.removeAll();

        try {
            Connection con = DBConnection.getConnection();

            String query = """
                SELECT b.booking_id, b.journey_date, b.total_passengers,
                       t.train_id, t.train_name, t.train_type,
                       t.departure, t.destination,
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
                  AND b.journey_date >= CURDATE()
                ORDER BY b.journey_date ASC
            """;

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            boolean found = false;

            while (rs.next()) {
                found = true;
                int    bookingId   = rs.getInt("booking_id");
                int    trainId     = rs.getInt("train_id");
                String trainName   = rs.getString("train_name");
                String trainType   = rs.getString("train_type");
                String dep         = rs.getString("departure");
                String dest        = rs.getString("destination");
                Date   journeyDate = rs.getDate("journey_date");
                int    pax         = rs.getInt("total_passengers");
                double baseFare    = rs.getDouble("base_fare");
                double farePerKm   = rs.getDouble("fare_per_km");
                int    km          = rs.getInt("total_km");
                double totalFare   = (baseFare + farePerKm * km) * pax;

                // Days until journey
                long daysLeft = (journeyDate.getTime() - System.currentTimeMillis()) / (1000 * 60 * 60 * 24);

                listPanel.add(buildBookingCard(
                        bookingId, trainId, trainName, trainType,
                        dep, dest, journeyDate,
                        pax, totalFare, daysLeft
                ));
                listPanel.add(Box.createVerticalStrut(12));
            }

            if (!found) {
                listPanel.add(Box.createVerticalStrut(80));
                JLabel empty = new JLabel("No upcoming bookings to cancel.", SwingConstants.CENTER);
                empty.setFont(new Font("Segoe UI", Font.PLAIN, 15));
                empty.setForeground(TEXT_GREY);
                empty.setAlignmentX(Component.CENTER_ALIGNMENT);
                listPanel.add(empty);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    // ────────────────────────────────────────────────────────────
    // BOOKING CARD
    // ────────────────────────────────────────────────────────────
    private JPanel buildBookingCard(int bookingId, int trainId, String trainName,
                                    String trainType, String dep, String dest,
                                    Date journeyDate, int pax,
                                    double totalFare, long daysLeft) {

        JPanel wrapper = new JPanel();
        wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
        wrapper.setOpaque(false);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

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

        // Train name + type
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        namePanel.setOpaque(false);
        JLabel nameLabel = new JLabel(trainName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        nameLabel.setForeground(PRIMARY_DARK);
        namePanel.add(nameLabel);
        namePanel.add(typeBadge(trainType));

        JLabel bookIdLabel = new JLabel("Booking #" + bookingId);
        bookIdLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        bookIdLabel.setForeground(TEXT_GREY);

        JPanel nameCol = new JPanel();
        nameCol.setLayout(new BoxLayout(nameCol, BoxLayout.Y_AXIS));
        nameCol.setOpaque(false);
        nameCol.add(namePanel);
        nameCol.add(bookIdLabel);

        gc.gridx = 0; gc.weightx = 2;
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

        // Refund info
        gc.gridx = 4; gc.weightx = 1;
        double refund = calculateRefund(totalFare, daysLeft);
        JPanel refundCol = infoCol("Refund",  "₹" + String.format("%.0f", refund));
        ((JLabel) refundCol.getComponent(1)).setForeground(refund > 0 ? GREEN : ERROR_RED);
        card.add(refundCol, gc);

        // Days left badge
        gc.gridx = 5; gc.weightx = 0.8;
        JLabel daysLabel = new JLabel(daysLeft + " day" + (daysLeft == 1 ? "" : "s") + " left") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = daysLeft <= 1 ? new Color(255, 230, 220)
                        : daysLeft <= 3 ? new Color(255, 245, 220)
                        : new Color(220, 245, 228);
                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 10, 10));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        daysLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        daysLabel.setForeground(daysLeft <= 1 ? ERROR_RED : daysLeft <= 3 ? WARN_ORANGE : GREEN);
        daysLabel.setBorder(new EmptyBorder(3, 10, 3, 10));
        daysLabel.setOpaque(false);

        JPanel daysCol = new JPanel();
        daysCol.setLayout(new BoxLayout(daysCol, BoxLayout.Y_AXIS));
        daysCol.setOpaque(false);
        JLabel daysHdr = new JLabel("Departure");
        daysHdr.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        daysHdr.setForeground(TEXT_GREY);
        daysHdr.setAlignmentX(Component.CENTER_ALIGNMENT);
        daysLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        daysCol.add(daysHdr);
        daysCol.add(Box.createVerticalStrut(4));
        daysCol.add(daysLabel);
        card.add(daysCol, gc);

        // Cancel button
        JButton cancelBtn = new JButton("Cancel") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(160, 30, 30) : ERROR_RED);
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
        cancelBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cancelBtn.setPreferredSize(new Dimension(80, 36));
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.setBorderPainted(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Confirm panel (hidden)
        JPanel confirmPanel = buildConfirmPanel(
                bookingId, trainId, trainName, journeyDate,
                pax, refund, cancelBtn, wrapper
        );
        confirmPanel.setVisible(false);

        cancelBtn.addActionListener(e -> {
            boolean showing = confirmPanel.isVisible();
            confirmPanel.setVisible(!showing);
            cancelBtn.setText(showing ? "Cancel" : "Close");
            wrapper.revalidate();
            wrapper.repaint();
            scrollPane.revalidate();
        });

        gc.gridx = 6; gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(0, 8, 0, 0);
        card.add(cancelBtn, gc);

        wrapper.add(card);
        wrapper.add(confirmPanel);
        return wrapper;
    }

    // ────────────────────────────────────────────────────────────
    // CONFIRM CANCELLATION PANEL
    // ────────────────────────────────────────────────────────────
    private JPanel buildConfirmPanel(int bookingId, int trainId, String trainName,
                                     Date journeyDate, int pax, double refund,
                                     JButton cancelBtn, JPanel wrapper) {

        JPanel panel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 245, 245));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 0, 0));
                g2.setColor(new Color(240, 200, 200));
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 0, 0));
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(14, 20, 14, 20));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(4, 8, 4, 8);

        // Warning message
        JLabel warning = new JLabel("⚠  Are you sure you want to cancel this booking?");
        warning.setFont(new Font("Segoe UI", Font.BOLD, 13));
        warning.setForeground(WARN_ORANGE);
        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 3;
        panel.add(warning, gc);

        // Summary
        JLabel summary = new JLabel(
                trainName + "  •  " + journeyDate + "  •  " + pax + " passenger(s)"
                        + "  •  Refund: ₹" + String.format("%.0f", refund)
        );
        summary.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        summary.setForeground(TEXT_GREY);
        gc.gridy = 1;
        panel.add(summary, gc);

        if (refund == 0) {
            JLabel noRefund = new JLabel("No refund applicable — journey is within 24 hours.");
            noRefund.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            noRefund.setForeground(ERROR_RED);
            gc.gridy = 2;
            panel.add(noRefund, gc);
        }

        // Status label
        JLabel statusLabel = new JLabel("", SwingConstants.LEFT);
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        gc.gridy = 3;
        panel.add(statusLabel, gc);

        // Buttons row
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setOpaque(false);

        JButton confirmBtn = actionButton("Yes, Cancel Booking", ERROR_RED);
        JButton keepBtn    = actionButton("Keep Booking", new Color(100, 80, 160));

        confirmBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this,
                    "This action cannot be undone.\nProceed with cancellation?",
                    "Confirm Cancellation", JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            if (choice != JOptionPane.YES_OPTION) return;

            try {
                Connection con = DBConnection.getConnection();
                BookingDAO bookingDAO = new BookingDAO(con);
                TrainDAO   trainDAO   = new TrainDAO(con);

                Booking booking = bookingDAO.getBookingById(bookingId);
                if (booking == null) {
                    statusLabel.setText("Booking not found.");
                    statusLabel.setForeground(ERROR_RED); return;
                }
                if (booking.getUserId() != userId) {
                    statusLabel.setText("Unauthorized.");
                    statusLabel.setForeground(ERROR_RED); return;
                }

                // Restore seats
                trainDAO.restoreSeats(trainId, journeyDate, pax);

                // Delete booking + passengers
                bookingDAO.deleteBooking(bookingId);

                statusLabel.setText("✓ Booking #" + bookingId + " cancelled. Refund: ₹"
                        + String.format("%.0f", refund));
                statusLabel.setForeground(GREEN);

                confirmBtn.setEnabled(false);
                keepBtn.setEnabled(false);
                cancelBtn.setEnabled(false);
                cancelBtn.setText("Cancelled");

                // Reload after short delay
                Timer t = new Timer(1500, ev -> loadUpcomingBookings());
                t.setRepeats(false);
                t.start();

            } catch (Exception ex) {
                ex.printStackTrace();
                statusLabel.setText("Error: " + ex.getMessage());
                statusLabel.setForeground(ERROR_RED);
            }
        });

        keepBtn.addActionListener(e -> {
            panel.setVisible(false);
            cancelBtn.setText("Cancel");
            wrapper.revalidate();
            wrapper.repaint();
        });

        btnRow.add(confirmBtn);
        btnRow.add(keepBtn);

        gc.gridy = 4; gc.gridwidth = 3;
        panel.add(btnRow, gc);

        return panel;
    }

    // ────────────────────────────────────────────────────────────
    // REFUND CALCULATION
    // Based on days before journey:
    // > 7 days  → 90% refund
    // 3-7 days  → 50% refund
    // 1-3 days  → 25% refund
    // < 1 day   → no refund
    // ────────────────────────────────────────────────────────────
    private double calculateRefund(double totalFare, long daysLeft) {
        if (daysLeft > 7)  return totalFare * 0.90;
        if (daysLeft >= 3) return totalFare * 0.50;
        if (daysLeft >= 1) return totalFare * 0.25;
        return 0;
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

    private JButton actionButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(isEnabled()
                        ? (getModel().isRollover() ? bg.darker() : bg)
                        : new Color(180, 180, 180));
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
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(180, 36));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
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
