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
    private static final Color PRIMARY = new Color(72, 52, 120);
    private static final Color PRIMARY_DARK = new Color(50, 35, 90);
    private static final Color ACCENT = new Color(155, 89, 182);
    private static final Color WHITE = Color.WHITE;
    private static final Color TEXT_GREY = new Color(100, 100, 100);
    private static final Color BG_OVERLAY = new Color(30, 20, 60, 160);

    private final int currentUserId;
    private final String userName;
    private final String userEmail;

    private BufferedImage bgImage;
    private JTextField fromField, toField;
    private DatePickerField dateField;

    public UserDashboard(int currentUserId, String userName, String userEmail) {
        this.currentUserId = currentUserId;
        this.userName = userName;
        this.userEmail = userEmail;

        // load background
        try {
            URL imgUrl = getClass().getClassLoader().getResource("images/background.jpg");
            if (imgUrl != null) bgImage = ImageIO.read(imgUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
        logo.setFont(new Font("Helvetica Neue", Font.BOLD, 20));
        logo.setForeground(WHITE);
        nav.add(logo, BorderLayout.WEST);

        // Nav links — removed My Bookings and Cancel since they're in quick action cards
        JPanel navLinks = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        navLinks.setOpaque(false);
        navLinks.add(navButton("Home", e -> {
            fromField.setText("e.g. New Delhi Junction");
            fromField.setForeground(TEXT_GREY);
            toField.setText("e.g. Mumbai Central");
            toField.setForeground(TEXT_GREY);
            dateField.setForeground(TEXT_GREY);
            fromField.requestFocus();
        }));
        nav.add(navLinks, BorderLayout.CENTER);

        // Profile + Logout (right)
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        rightPanel.setOpaque(false);

        // Profile button
        JButton profileBtn = new JButton("👤 " + userName);
        profileBtn.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
        profileBtn.setForeground(WHITE);
        profileBtn.setBackground(ACCENT);
        profileBtn.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        profileBtn.setFocusPainted(false);
        profileBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profileBtn.addActionListener(e -> showProfilePopup(profileBtn));

        // Logout button
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
        logoutBtn.setForeground(WHITE);
        logoutBtn.setBackground(new Color(180, 50, 50));
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to logout?", "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                new WelcomeScreen();
            }
        });

        rightPanel.add(profileBtn);
        rightPanel.add(logoutBtn);
        nav.add(rightPanel, BorderLayout.EAST);

        return nav;
    }

    private JButton navButton(String text, ActionListener action) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        btn.setForeground(WHITE);
        btn.setBackground(PRIMARY_DARK);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(ACCENT);
            }

            public void mouseExited(MouseEvent e) {
                btn.setForeground(WHITE);
            }
        });
        if (action != null) btn.addActionListener(action);
        return btn;
    }

    // ────────────────────────────────────────────────────────────
    // MAIN PANEL
    // ────────────────────────────────────────────────────────────
    private JPanel buildMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                // Background
                if (bgImage != null) {
                    g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    g2.setPaint(new GradientPaint(0, 0, PRIMARY_DARK, getWidth(), getHeight(), ACCENT));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }

                // Dark overlay
                g2.setColor(BG_OVERLAY);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Welcome text drawn directly — avoids macOS rendering gaps
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setColor(WHITE);
                g2.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
                String text = "Hello, " + userName + "! Where are you travelling today?";
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                g2.drawString(text, x, 80);
            }
        };
        mainPanel.setOpaque(false);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;

        // Search card — pushed down to leave room for painted text
        gc.gridy = 0;
        gc.insets = new Insets(60, 0, 0, 0);
        mainPanel.add(buildSearchCard(), gc);

        // Quick action cards
        gc.gridy = 1;
        gc.insets = new Insets(30, 0, 0, 0);

        JPanel actionsWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        actionsWrapper.setOpaque(false);
        actionsWrapper.add(quickCard("📋", "My Bookings",   "View your booking history", e -> openBookingHistory()));
        actionsWrapper.add(quickCard("❌", "Cancel Ticket", "Cancel a booking",          e -> openCancelBooking()));
        mainPanel.add(actionsWrapper, gc);

        return mainPanel;
    }

    // ────────────────────────────────────────────────────────────
    // SEARCH CARD
    // ────────────────────────────────────────────────────────────
    private JPanel buildSearchCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 230));
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(820, 120));
        card.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 20));

        // Create fields first
        fromField = styledSearchField("e.g. New Delhi Junction");
        toField = styledSearchField("e.g. Mumbai Central");
        dateField = new DatePickerField("Select Date");

        // Add labeled columns
        card.add(labeledSearchCol("From", fromField));
        card.add(labeledSearchCol("To", toField));
        JPanel dateCol = new JPanel(new BorderLayout(0, 4));
        dateCol.setOpaque(false);
        JLabel dateLbl = new JLabel("Journey Date");
        dateLbl.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
        dateLbl.setForeground(PRIMARY);
        dateCol.add(dateLbl, BorderLayout.NORTH);
        dateCol.add(dateField, BorderLayout.CENTER);
        card.add(dateCol);

        // Search button
        JButton searchBtn = new JButton("Search") {
            @Override
            protected void paintComponent(Graphics g) {
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
        searchBtn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        searchBtn.setPreferredSize(new Dimension(120, 38));
        searchBtn.setContentAreaFilled(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.addActionListener(e -> handleSearch());

        // Align button with fields
        JPanel btnCol = new JPanel(new BorderLayout());
        btnCol.setOpaque(false);
        JLabel spacer = new JLabel(" ");
        spacer.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        btnCol.add(spacer, BorderLayout.NORTH);
        btnCol.add(searchBtn, BorderLayout.CENTER);
        card.add(btnCol);

        return card;
    }

    private JTextField styledSearchField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WHITE);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
                g2.setColor(new Color(200, 180, 220));
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 15, 15));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        field.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        field.setForeground(TEXT_GREY);
        field.setText(placeholder);
        field.setPreferredSize(new Dimension(200, 38));
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(TEXT_GREY);
                }
            }
        });
        return field;
    }

    private JPanel labeledSearchCol(String labelText, JTextField field) {
        JPanel col = new JPanel(new BorderLayout(0, 4));
        col.setOpaque(false);
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        lbl.setForeground(PRIMARY);
        col.add(lbl, BorderLayout.NORTH);
        col.add(field, BorderLayout.CENTER);
        return col;
    }

    // ────────────────────────────────────────────────────────────
    // QUICK ACTION CARDS
    // ────────────────────────────────────────────────────────────
    private JPanel buildQuickActions() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panel.setOpaque(false);

        panel.add(quickCard("🎫", "Book Ticket", "Search & book your seat", e -> scrollToSearch()));
        panel.add(quickCard("📋", "My Bookings", "View your booking history", e -> openBookingHistory()));
        panel.add(quickCard("❌", "Cancel Ticket", "Cancel a booking", e -> openCancelBooking()));
        panel.add(quickCard("🔍", "Search Trains", "Find trains on a route", e -> scrollToSearch()));

        return panel;
    }

    private JPanel quickCard(String icon, String title, String desc, ActionListener action) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
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
        gc.gridx = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(4, 10, 2, 10);

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Helvetica Neue Emoji", Font.PLAIN, 26));
        gc.gridy = 0;
        card.add(iconLabel, gc);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        titleLabel.setForeground(PRIMARY_DARK);
        gc.gridy = 1;
        card.add(titleLabel, gc);

        JLabel descLabel = new JLabel(desc, SwingConstants.CENTER);
        descLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 11));
        descLabel.setForeground(TEXT_GREY);
        gc.gridy = 2;
        card.add(descLabel, gc);

        // hover effect
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBorder(BorderFactory.createLineBorder(ACCENT, 1, true));
            }

            public void mouseExited(MouseEvent e) {
                card.setBorder(null);
            }

            public void mouseClicked(MouseEvent e) {
                if (action != null) action.actionPerformed(null);
            }
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
        nameLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 13));
        nameLabel.setForeground(PRIMARY);
        nameLabel.setBorder(BorderFactory.createEmptyBorder(8, 8, 4, 8));
        popup.add(nameLabel);

        JLabel emailLabel = new JLabel("  ✉ " + userEmail);
        emailLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        emailLabel.setForeground(TEXT_GREY);
        emailLabel.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        popup.add(emailLabel);

        popup.addSeparator();

        JMenuItem logoutItem = new JMenuItem("Logout");
        logoutItem.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
        logoutItem.setForeground(new Color(180, 50, 50));
        logoutItem.addActionListener(e -> {
            dispose();
            new WelcomeScreen();
        });
        popup.add(logoutItem);

        popup.show(anchor, 0, anchor.getHeight());
    }

    // ────────────────────────────────────────────────────────────
    // HANDLERS
    // ────────────────────────────────────────────────────────────
    private void handleSearch() {
        String from = fromField.getText().trim();
        String to = toField.getText().trim();
        String date = dateField.getSelectedDateSQL();

        if (from.isEmpty() || from.equals("e.g. New Delhi Junction") ||
                to.isEmpty()   || to.equals("e.g. Mumbai Central")) {
            if (date == null) {
                JOptionPane.showMessageDialog(this, "Please select a journey date.", "Missing Info", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this,
                    "Please fill all fields.", "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
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
}