package com.smartrail.gui;

import com.smartrail.dao.BookingDAO;
import com.smartrail.dao.PassengerDAO;
import com.smartrail.dao.TrainDAO;
import com.smartrail.model.Passenger;
import com.smartrail.util.DBConnection;
import com.smartrail.util.EmailService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class PaymentGUI extends JFrame {

    private static final Color PRIMARY      = new Color(72, 52, 120);
    private static final Color PRIMARY_DARK = new Color(50, 35, 90);
    private static final Color ACCENT       = new Color(155, 89, 182);
    private static final Color WHITE        = Color.WHITE;
    private static final Color TEXT_GREY    = new Color(100, 100, 100);
    private static final Color BG_LIGHT     = new Color(245, 240, 255);
    private static final Color DIVIDER      = new Color(220, 210, 235);
    private static final Color GREEN        = new Color(40, 160, 80);
    private static final Color ERROR_RED    = new Color(200, 50, 50);

    // Booking details passed from SearchResultGUI
    private final int userId;
    private final int trainId;
    private final String trainName;
    private final Date journeyDate;
    private final List<Passenger> passengers;
    private final double totalFare;

    private String selectedMethod = null;
    private JLabel statusLabel;
    private JButton payBtn;

    public PaymentGUI(int userId, int trainId, String trainName,
                      Date journeyDate, List<Passenger> passengers,
                      double totalFare) {
        this.userId      = userId;
        this.trainId     = trainId;
        this.trainName   = trainName;
        this.journeyDate = journeyDate;
        this.passengers  = passengers;
        this.totalFare   = totalFare;

        setTitle("RailMatrix — Payment");
        setSize(520, 680);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel root = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, BG_LIGHT, 0, getHeight(), new Color(225, 210, 255)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setLayout(new BorderLayout());
        setContentPane(root);

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildBody(), BorderLayout.CENTER);

        setVisible(true);
    }

    // ────────────────────────────────────────────────────────────
    // HEADER
    // ────────────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PRIMARY_DARK);
        header.setBorder(new EmptyBorder(14, 20, 14, 20));

        JLabel logo = new JLabel("🚆 RailMatrix — Secure Payment");
        logo.setFont(new Font("Helvetica Neue", Font.BOLD, 16));
        logo.setForeground(WHITE);
        header.add(logo, BorderLayout.WEST);

        JLabel lock = new JLabel("🔒 SSL Secured");
        lock.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        lock.setForeground(new Color(180, 220, 180));
        header.add(lock, BorderLayout.EAST);

        return header;
    }

    // ────────────────────────────────────────────────────────────
    // BODY
    // ────────────────────────────────────────────────────────────
    private JPanel buildBody() {
        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setOpaque(false);
        body.setBorder(new EmptyBorder(20, 24, 20, 24));

        // Order summary card
        body.add(buildSummaryCard());
        body.add(Box.createVerticalStrut(16));

        // Payment method label
        JLabel methodLabel = new JLabel("Select Payment Method");
        methodLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 15));
        methodLabel.setForeground(PRIMARY_DARK);
        methodLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(methodLabel);
        body.add(Box.createVerticalStrut(12));

        // Payment options
        JPanel methodsPanel = new JPanel(new GridLayout(2, 3, 12, 12));
        methodsPanel.setOpaque(false);
        methodsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        methodsPanel.add(paymentBtn("GPay",    "🟢", new Color(66, 133, 244)));
        methodsPanel.add(paymentBtn("PhonePe", "🟣", new Color(103, 58, 183)));
        methodsPanel.add(paymentBtn("Paytm",   "🔵", new Color(0, 150, 136)));
        methodsPanel.add(paymentBtn("UPI",     "🔴", new Color(230, 74, 25)));
        methodsPanel.add(paymentBtn("Card",    "💳", new Color(55, 71, 79)));
        methodsPanel.add(paymentBtn("NetBanking", "🏦", new Color(30, 136, 229)));

        body.add(methodsPanel);
        body.add(Box.createVerticalStrut(16));

        // QR code section (shown after method selected)
        JPanel qrPanel = buildQRPanel();
        qrPanelRef = qrPanel;
        qrPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(qrPanel);
        body.add(Box.createVerticalStrut(16));

        // Status label
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        body.add(statusLabel);
        body.add(Box.createVerticalStrut(12));

        // Pay button
        payBtn = buildPayButton();
        payBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        payBtn.setEnabled(false);
        body.add(payBtn);
        body.add(Box.createVerticalStrut(10));

        // Cancel button
        JButton cancelBtn = new JButton("Cancel") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(240, 235, 255));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 14, 14));
                g2.setColor(PRIMARY);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        cancelBtn.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        cancelBtn.setPreferredSize(new Dimension(440, 42));
        cancelBtn.setMaximumSize(new Dimension(440, 42));
        cancelBtn.setContentAreaFilled(false);
        cancelBtn.setBorderPainted(false);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        cancelBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelBtn.addActionListener(e -> dispose());
        body.add(cancelBtn);

        return body;
    }

    // ────────────────────────────────────────────────────────────
    // ORDER SUMMARY CARD
    // ────────────────────────────────────────────────────────────
    private JPanel buildSummaryCard() {
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
        card.setBorder(new EmptyBorder(14, 16, 14, 16));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(3, 4, 3, 4);

        // Title
        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 2;
        JLabel title = new JLabel("Order Summary");
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        title.setForeground(PRIMARY_DARK);
        card.add(title, gc);

        gc.gridwidth = 1;

        // Train
        gc.gridy = 1; gc.gridx = 0; gc.weightx = 1;
        card.add(summaryLabel("Train"), gc);
        gc.gridx = 1;
        card.add(summaryValue(trainName), gc);

        // Journey date
        gc.gridy = 2; gc.gridx = 0;
        card.add(summaryLabel("Journey Date"), gc);
        gc.gridx = 1;
        card.add(summaryValue(journeyDate.toString()), gc);

        // Passengers
        gc.gridy = 3; gc.gridx = 0;
        card.add(summaryLabel("Passengers"), gc);
        gc.gridx = 1;
        card.add(summaryValue(String.valueOf(passengers.size())), gc);

        // Divider line
        gc.gridy = 4; gc.gridx = 0; gc.gridwidth = 2;
        JSeparator sep = new JSeparator();
        sep.setForeground(DIVIDER);
        card.add(sep, gc);

        // Total fare
        gc.gridy = 5; gc.gridwidth = 1; gc.gridx = 0;
        JLabel fareLabel = new JLabel("Total Amount");
        fareLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        fareLabel.setForeground(PRIMARY_DARK);
        card.add(fareLabel, gc);

        gc.gridx = 1;
        JLabel fareValue = new JLabel("₹" + String.format("%.0f", totalFare), SwingConstants.RIGHT);
        fareValue.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
        fareValue.setForeground(PRIMARY);
        card.add(fareValue, gc);

        return card;
    }

    // ────────────────────────────────────────────────────────────
    // QR CODE PANEL
    // ────────────────────────────────────────────────────────────
    private JPanel buildQRPanel() {
        JPanel panel = new JPanel(new BorderLayout(12, 0)) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(248, 244, 255));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 16, 16));
                g2.setColor(DIVIDER);
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 16, 16));
                g2.dispose();
            }
        };
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(12, 14, 12, 14));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));
        panel.setVisible(false); // shown after method selected

        // QR code drawn programmatically
        JPanel qrCode = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(WHITE);
                g2.fillRect(0, 0, 80, 80);
                g2.setColor(Color.BLACK);
                // Draw a simple mock QR pattern
                int[][] qr = {
                        {1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1},
                        {1,0,0,0,0,0,1,0,0,1,1,0,0,0,0,0,1},
                        {1,0,1,1,1,0,1,0,1,0,1,0,1,1,1,0,1},
                        {1,0,1,1,1,0,1,0,0,1,0,0,1,1,1,0,1},
                        {1,0,1,1,1,0,1,0,1,1,1,0,1,1,1,0,1},
                        {1,0,0,0,0,0,1,0,0,0,1,0,0,0,0,0,1},
                        {1,1,1,1,1,1,1,0,1,0,1,1,1,1,1,1,1},
                        {0,0,0,0,0,0,0,0,1,1,0,1,0,0,0,0,0},
                        {1,0,1,1,0,1,1,1,0,1,1,0,1,0,1,1,0},
                        {0,1,0,1,1,0,0,0,1,0,0,1,0,1,0,1,1},
                        {1,1,1,0,1,1,1,1,0,1,1,0,1,1,1,0,1},
                        {0,0,0,0,0,0,0,0,1,0,1,1,0,0,1,0,0},
                        {1,1,1,1,1,1,1,0,0,1,0,0,1,0,1,1,0},
                        {1,0,0,0,0,0,1,0,1,1,1,0,0,1,0,0,1},
                        {1,0,1,1,1,0,1,0,0,0,1,1,1,0,1,1,0},
                        {1,0,0,0,0,0,1,0,1,0,0,1,0,1,0,1,0},
                        {1,1,1,1,1,1,1,0,0,1,1,0,1,0,1,0,1}
                };
                int cell = 80 / qr.length;
                for (int r = 0; r < qr.length; r++) {
                    for (int c = 0; c < qr[r].length; c++) {
                        if (qr[r][c] == 1) {
                            g2.fillRect(c * cell, r * cell, cell, cell);
                        }
                    }
                }
                g2.dispose();
            }
        };
        qrCode.setPreferredSize(new Dimension(80, 80));
        qrCode.setOpaque(false);

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setOpaque(false);

        JLabel scanLabel = new JLabel("Scan to Pay");
        scanLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        scanLabel.setForeground(PRIMARY_DARK);

        JLabel upiLabel = new JLabel("UPI ID: railmatrix@upi");
        upiLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        upiLabel.setForeground(TEXT_GREY);

        JLabel amtLabel = new JLabel("Amount: ₹" + String.format("%.0f", totalFare));
        amtLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        amtLabel.setForeground(PRIMARY);

        JLabel noteLabel = new JLabel("<html><i>After payment, click<br>'Confirm Payment' below</i></html>");
        noteLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 11));
        noteLabel.setForeground(TEXT_GREY);

        info.add(scanLabel);
        info.add(Box.createVerticalStrut(4));
        info.add(upiLabel);
        info.add(Box.createVerticalStrut(4));
        info.add(amtLabel);
        info.add(Box.createVerticalStrut(6));
        info.add(noteLabel);

        panel.add(qrCode, BorderLayout.WEST);
        panel.add(info, BorderLayout.CENTER);

        return panel;
    }

    // ────────────────────────────────────────────────────────────
    // PAYMENT METHOD BUTTON
    // ────────────────────────────────────────────────────────────
    private JButton[] methodButtons = new JButton[6];
    private int methodIndex = 0;
    private JPanel qrPanelRef = null;

    private JButton paymentBtn(String name, String emoji, Color accent) {
        JButton btn = new JButton() {
            boolean selected = false;
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = selected ? new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 40)
                        : WHITE;
                Color border = selected ? accent : DIVIDER;
                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(border);
                g2.setStroke(new BasicStroke(selected ? 2 : 1));
                g2.draw(new RoundRectangle2D.Double(1, 1, getWidth()-2, getHeight()-2, 12, 12));

                g2.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
                FontMetrics efm = g2.getFontMetrics();
                g2.setColor(Color.BLACK);
                g2.drawString(emoji, (getWidth() - efm.stringWidth(emoji)) / 2, 28);

                g2.setFont(new Font("Helvetica Neue", Font.BOLD, 11));
                g2.setColor(selected ? accent.darker() : TEXT_GREY);
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(name, (getWidth() - fm.stringWidth(name)) / 2, 44);
                g2.dispose();
            }

            public void setSelected(boolean s) { selected = s; repaint(); }
            public boolean isSelected() {
                try { return (boolean) getClass().getMethod("isSelected").invoke(this); }
                catch (Exception e) { return false; }
            }
        };

        btn.setPreferredSize(new Dimension(80, 55));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        int idx = methodIndex++;
        methodButtons[idx] = btn;

        btn.addActionListener(e -> {
            // Deselect all
            for (JButton b : methodButtons) {
                if (b != null) {
                    try { b.getClass().getMethod("setSelected", boolean.class).invoke(b, false); }
                    catch (Exception ex) { ex.printStackTrace(); }
                }
            }
            // Select this
            try { btn.getClass().getMethod("setSelected", boolean.class).invoke(btn, true); }
            catch (Exception ex) { ex.printStackTrace(); }

            selectedMethod = name;
            payBtn.setEnabled(true);
            statusLabel.setText("Selected: " + name + " — click Confirm Payment to proceed");
            statusLabel.setForeground(PRIMARY);

            // Show QR panel
            if (qrPanelRef != null) {
                qrPanelRef.setVisible(true);
                revalidate(); repaint();
            }
        });

        return btn;
    }

    // ────────────────────────────────────────────────────────────
    // PAY BUTTON
    // ────────────────────────────────────────────────────────────
    private JButton buildPayButton() {
        JButton btn = new JButton("Confirm Payment") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = isEnabled()
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
        btn.setFont(new Font("Helvetica Neue", Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(440, 46));
        btn.setMaximumSize(new Dimension(440, 46));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> processPayment());
        return btn;
    }

    // ────────────────────────────────────────────────────────────
    // PROCESS PAYMENT & CONFIRM BOOKING
    // ────────────────────────────────────────────────────────────
    private void processPayment() {
        if (selectedMethod == null) {
            statusLabel.setText("Please select a payment method.");
            statusLabel.setForeground(ERROR_RED);
            return;
        }

        // Simulate payment processing
        payBtn.setEnabled(false);
        payBtn.setText("Processing...");
        statusLabel.setText("Verifying payment via " + selectedMethod + "...");
        statusLabel.setForeground(TEXT_GREY);

        Timer processingTimer = new Timer(2000, e -> {
            // Simulate success (90% of time) or failure
            boolean paymentSuccess = Math.random() > 0.1;

            if (paymentSuccess) {
                statusLabel.setText("Payment verified! Confirming booking...");
                statusLabel.setForeground(GREEN);

                // Now do actual booking in DB
                Timer bookingTimer = new Timer(800, ev -> {
                    try {
                        Connection con = DBConnection.getConnection();
                        TrainDAO trainDAO     = new TrainDAO(con);
                        BookingDAO bookingDAO = new BookingDAO(con);
                        PassengerDAO paxDAO   = new PassengerDAO(con);

                        int totalSeats = trainDAO.getTotalSeats(trainId);
                        trainDAO.createScheduleIfNotExists(trainId, journeyDate, totalSeats);

                        int avail = trainDAO.getAvailableSeats(trainId, journeyDate);
                        if (avail < passengers.size()) {
                            statusLabel.setText("Payment received but seats no longer available. Refund initiated.");
                            statusLabel.setForeground(ERROR_RED);
                            payBtn.setText("Seats Unavailable");
                            return;
                        }

                        int bookingId = bookingDAO.bookSeats(userId, trainId, journeyDate, passengers.size());
                        if (bookingId == -1) {
                            statusLabel.setText("Booking failed. Refund will be processed.");
                            statusLabel.setForeground(ERROR_RED);
                            return;
                        }

                        for (int i = 0; i < passengers.size(); i++) {
                            paxDAO.addPassenger(passengers.get(i), bookingId, i + 1);
                        }

                        // Send confirmation email in background thread
                        String userEmail = getUserEmail(userId);
                        String userName  = getUserName(userId);
                        if (userEmail != null) {
                            new Thread(() ->
                                    EmailService.sendBookingConfirmation(
                                            userEmail, userName,
                                            bookingId, trainName,
                                            "", "", // departure/destination
                                            journeyDate.toString(),
                                            passengers.size(),
                                            totalFare,
                                            selectedMethod
                                    )
                            ).start();
                        }

                        // Show success dialog
                        showSuccessDialog(bookingId);
                        dispose();

                    } catch (Exception ex) {
                        ex.printStackTrace();
                        statusLabel.setText("Error: " + ex.getMessage());
                        statusLabel.setForeground(ERROR_RED);
                        payBtn.setEnabled(true);
                        payBtn.setText("Retry");
                    }
                });
                bookingTimer.setRepeats(false);
                bookingTimer.start();

            } else {
                // Payment failed
                statusLabel.setText("Payment failed. Please try again.");
                statusLabel.setForeground(ERROR_RED);
                payBtn.setEnabled(true);
                payBtn.setText("Retry Payment");
            }
        });
        processingTimer.setRepeats(false);
        processingTimer.start();
    }

    // ────────────────────────────────────────────────────────────
    // SUCCESS DIALOG
    // ────────────────────────────────────────────────────────────
    private void showSuccessDialog(int bookingId) {
        JDialog dialog = new JDialog((Frame) null, "Booking Confirmed", true);
        dialog.setSize(380, 280);
        dialog.setLocationRelativeTo(null);
        dialog.setUndecorated(false);

        JPanel panel = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setPaint(new GradientPaint(0, 0, BG_LIGHT, 0, getHeight(), new Color(225, 210, 255)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(6, 20, 4, 20);

        JLabel tick = new JLabel("✅", SwingConstants.CENTER);
        tick.setFont(new Font("Helvetica Neue", Font.PLAIN, 48));
        gc.gridy = 0; panel.add(tick, gc);

        JLabel title = new JLabel("Booking Confirmed!", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        title.setForeground(GREEN);
        gc.gridy = 1; panel.add(title, gc);

        JLabel bIdLabel = new JLabel("Booking ID: #" + bookingId, SwingConstants.CENTER);
        bIdLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 15));
        bIdLabel.setForeground(PRIMARY);
        gc.gridy = 2; panel.add(bIdLabel, gc);

        JLabel trainLabel = new JLabel(trainName + " | " + journeyDate, SwingConstants.CENTER);
        trainLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
        trainLabel.setForeground(TEXT_GREY);
        gc.gridy = 3; panel.add(trainLabel, gc);

        JLabel paidLabel = new JLabel("Paid via " + selectedMethod + ": ₹" + String.format("%.0f", totalFare), SwingConstants.CENTER);
        paidLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        paidLabel.setForeground(PRIMARY_DARK);
        gc.gridy = 4; panel.add(paidLabel, gc);

        JButton doneBtn = new JButton("Done") {
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
        doneBtn.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        doneBtn.setPreferredSize(new Dimension(300, 42));
        doneBtn.setContentAreaFilled(false);
        doneBtn.setBorderPainted(false);
        doneBtn.setFocusPainted(false);
        doneBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        doneBtn.addActionListener(e -> dialog.dispose());
        gc.gridy = 5; gc.insets = new Insets(16, 20, 16, 20);
        panel.add(doneBtn, gc);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    // ── UI helpers ───────────────────────────────────────────────
    private JLabel summaryLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        l.setForeground(TEXT_GREY);
        return l;
    }

    private JLabel summaryValue(String text) {
        JLabel l = new JLabel(text, SwingConstants.RIGHT);
        l.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
        l.setForeground(PRIMARY_DARK);
        return l;
    }

    private String getUserEmail(int userId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT email FROM users WHERE user_id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("email");
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    private String getUserName(int userId) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "SELECT name FROM users WHERE user_id = ?");
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("name");
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}