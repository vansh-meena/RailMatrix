package com.smartrail.gui;

import com.smartrail.dao.TrainDAO;
import com.smartrail.util.DBConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;

public class UserDashboard extends JFrame {

    // ── Colour palette ───────────────────────────────────────────
    private static final Color PRIMARY       = new Color(72, 52, 120);
    private static final Color PRIMARY_DARK  = new Color(50, 35, 90);
    private static final Color ACCENT        = new Color(155, 89, 182);
    private static final Color WHITE         = Color.WHITE;
    private static final Color TEXT_GREY     = new Color(100, 100, 100);
    private static final Color BG_OVERLAY    = new Color(30, 20, 60, 160);

    private final int currentUserId;
    private final String userName;
    private final String userEmail;

    private BufferedImage bgImage;
    private JTextField fromField, toField, dateField;

    public UserDashboard(int currentUserId, String userName, String userEmail) {
        this.currentUserId = currentUserId;
        this.userName      = userName;
        this.userEmail     = userEmail;

        // load background
        try {
            URL imgUrl = getClass().getClassLoader().getResource("images/background.jpg");
            if (imgUrl != null) bgImage = ImageIO.read(imgUrl);
        } catch (IOException e) { e.printStackTrace(); }

        setTitle("RailMatrix — Dashboard");
        setSize(1100, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildNavbar(), BorderLayout.NORTH);
        add(buildMainPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    // ────────────────────────────────────────────────────────────
    // NAVBAR
    // ────────────────────────────────────────────────────────────
    private JPanel buildNavbar() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(PRIMARY_DARK);
        nav.setPreferredSize(new Dimension(getWidth(), 55));
        nav.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Logo
        JLabel logo = new JLabel("🚆 RailMatrix");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        logo.setForeground(WHITE);
        nav.add(logo, BorderLayout.WEST);

        // Nav links
        JPanel navLinks = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        navLinks.setOpaque(false);
        navLinks.add(navButton("Home",     e -> scrollToSearch()));
        navLinks.add(navButton("My Bookings", e -> openBookingHistory()));
        navLinks.add(navButton("Cancel Ticket", e -> openCancelBooking()));
        nav.add(navLinks, BorderLayout.CENTER);

        // Profile + Logout (right)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        rightPanel.setOpaque(false);

        // Profile button
        JButton profileBtn = new JButton("👤 " + userName);
        profileBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        profileBtn.setForeground(WHITE);
        profileBtn.setBackground(ACCENT);
        profileBtn.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        profileBtn.setFocusPainted(false);
        profileBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profileBtn.addActionListener(e -> showProfilePopup(profileBtn));

        // Logout button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        logoutBtn.setForeground(WHITE);
        logoutBtn.setBackground(new Color(180, 50, 50));
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?", "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) { dispose(); new WelcomeScreen(); }
        });

        rightPanel.add(profileBtn);
        rightPanel.add(logoutBtn);
        nav.add(rightPanel, BorderLayout.EAST);

        return nav;
    }

    private JButton navButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(WHITE);
        btn.setBackground(PRIMARY_DARK);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setForeground(ACCENT); }
            public void mouseExited(MouseEvent e)  { btn.setForeground(WHITE);  }
        });
        if (action != null) btn.addActionListener(action);
        return btn;
    }

    // ────────────────────────────────────────────────────────────
    // MAIN PANEL
    // ────────────────────────────────────────────────────────────
    private JPanel buildMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                if (bgImage != null) {
                    g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g2.setPaint(new GradientPaint(0, 0, PRIMARY_DARK, getWidth(), getHeight(), ACCENT));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
                g2.setColor(BG_OVERLAY);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.gridy = 0;
        gc.insets = new Insets(0, 0, 20, 0);

        // Welcome label
        JLabel welcome = new JLabel("Hello, " + userName + "! Where are you travelling today?", SwingConstants.CENTER);
        welcome.setFont(new Font("Segoe UI", Font.BOLD, 20));
        welcome.setForeground(WHITE);
        mainPanel.add(welcome, gc);

        // Search card
        gc.gridy = 1; gc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(buildSearchCard(), gc);

        // Quick action cards
        gc.gridy = 2; gc.insets = new Insets(30, 0, 0, 0);
        mainPanel.add(buildQuickActions(), gc);

        return mainPanel;
    }

    // ────────────────────────────────────────────────────────────
    // SEARCH CARD
    // ────────────────────────────────────────────────────────────
    private JPanel buildSearchCard() {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 230));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(780, 160));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(12, 14, 4, 14);
        gc.gridy = 0;

        // Labels row
        gc.gridx = 0; card.add(fieldLabel("From"), gc);
        gc.gridx = 1; card.add(fieldLabel("To"), gc);
        gc.gridx = 2; card.add(fieldLabel("Journey Date"), gc);
        gc.gridx = 3; card.add(new JLabel(""), gc); // spacer

        // Fields row
        fromField = roundedField("e.g. New Delhi Junction");
        toField   = roundedField("e.g. Mumbai Central");
        dateField = roundedField("YYYY-MM-DD");

        gc.gridy = 1; gc.insets = new Insets(0, 14, 14, 14);
        gc.gridx = 0; card.add(fromField, gc);
        gc.gridx = 1; card.add(toField, gc);
        gc.gridx = 2; card.add(dateField, gc);

        // Search button
        JButton searchBtn = new JButton("🔍 Search") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? PRIMARY_DARK : PRIMARY);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                g2.dispose();
            }
        };
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        searchBtn.setPreferredSize(new Dimension(130, 40));
        searchBtn.setContentAreaFilled(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.addActionListener(e -> handleSearch());

        gc.gridx = 3;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        card.add(searchBtn, gc);

        return card;
    }

    // ────────────────────────────────────────────────────────────
    // QUICK ACTION CARDS
    // ────────────────────────────────────────────────────────────
    private JPanel buildQuickActions() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setOpaque(false);

        panel.add(quickCard("🎫", "Book Ticket",      "Search & book your seat",   e -> scrollToSearch()));
        panel.add(quickCard("📋", "My Bookings",      "View your booking history", e -> openBookingHistory()));
        panel.add(quickCard("❌", "Cancel Ticket",    "Cancel a booking",          e -> openCancelBooking()));
        panel.add(quickCard("🔍", "Search Trains",    "Find trains on a route",    e -> scrollToSearch()));

        return panel;
    }

    private JPanel quickCard(String icon, String title, String desc, ActionListener action) {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 200));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(170, 110));
        card.setLayout(new GridBagLayout());
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0; gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(4, 10, 2, 10);

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 26));
        gc.gridy = 0; card.add(iconLabel, gc);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        titleLabel.setForeground(PRIMARY_DARK);
        gc.gridy = 1; card.add(titleLabel, gc);

        JLabel descLabel = new JLabel(desc, SwingConstants.CENTER);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        descLabel.setForeground(TEXT_GREY);
        gc.gridy = 2; card.add(descLabel, gc);

        // hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { card.setBorder(BorderFactory.createLineBorder(ACCENT, 1, true)); }
            public void mouseExited(MouseEvent e)  { card.setBorder(null); }
            public void mouseClicked(MouseEvent e) { if (action != null) action.actionPerformed(null); }
        });

        return card;
    }

    // ────────────────────────────────────────────────────────────
    // PROFILE POPUP
    // ────────────────────────────────────────────────────────────
    private void showProfilePopup(JButton anchor) {
        JPopupMenu popup = new JPopupMenu();
        popup.setBorder(BorderFactory.createLineBorder(new Color(200, 180, 220)));

        JLabel nameLabel = new JLabel("  👤 " + userName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nameLabel.setForeground(PRIMARY);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));
        popup.add(nameLabel);

        JLabel emailLabel = new JLabel("  ✉ " + userEmail);
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        emailLabel.setForeground(TEXT_GREY);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        popup.add(emailLabel);

        popup.addSeparator();

        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        logoutItem.setForeground(new Color(180, 50, 50));
        logoutItem.addActionListener(e -> { dispose(); new WelcomeScreen(); });
        popup.add(logoutItem);

        popup.show(anchor, 0, anchor.getHeight());
    }

    // ────────────────────────────────────────────────────────────
    // HANDLERS
    // ────────────────────────────────────────────────────────────
    private void handleSearch() {
        String from = fromField.getText().trim();
        String to   = toField.getText().trim();
        String date = dateField.getText().trim();

        if (from.isEmpty() || from.equals("e.g. New Delhi Junction") ||
                to.isEmpty()   || to.equals("e.g. Mumbai Central")       ||
                date.isEmpty() || date.equals("YYYY-MM-DD")) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.",
                    "Missing Info", JOptionPane.WARNING_MESSAGE); return;
        }
        try {
            Date journeyDate = Date.valueOf(date);
            new SearchResultGUI(from, to, journeyDate, currentUserId);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, "Invalid date. Use YYYY-MM-DD.",
                    "Date Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void scrollToSearch() {
        fromField.requestFocus();
    }

    private void openBookingHistory() {
        new BookingHistoryGUI(currentUserId);
    }

    private void openCancelBooking() {
        new CancelBookingGUI(currentUserId);
    }

    // ────────────────────────────────────────────────────────────
    // UI HELPERS
    // ────────────────────────────────────────────────────────────
    private JLabel fieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(PRIMARY);
        return lbl;
    }

    private JTextField roundedField(String placeholder) {
        JTextField field = new JTextField(16);
        field.setOpaque(true);
        field.setBackground(WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                new RegisterGUI.RoundedBorder(15, new Color(200, 180, 220)),
                BorderFactory.createEmptyBorder(6, 12, 6, 12)
        ));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT_GREY);
        field.setText(placeholder);
        field.setPreferredSize(new Dimension(200, 38));
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) { field.setText(""); field.setForeground(Color.BLACK); }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) { field.setText(placeholder); field.setForeground(TEXT_GREY); }
            }
        });
        return field;
    }
}