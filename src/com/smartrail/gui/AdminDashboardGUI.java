package com.smartrail.gui;

import com.smartrail.util.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class AdminDashboardGUI extends JFrame {

    private static final Color PRIMARY      = new Color(72, 52, 120);
    private static final Color PRIMARY_DARK = new Color(50, 35, 90);
    private static final Color ACCENT       = new Color(155, 89, 182);
    private static final Color WHITE        = Color.WHITE;
    private static final Color TEXT_GREY    = new Color(100, 100, 100);
    private static final Color BG_LIGHT     = new Color(245, 240, 255);
    private static final Color DIVIDER      = new Color(220, 210, 235);
    private static final Color GREEN        = new Color(40, 160, 80);
    private static final Color ERROR_RED    = new Color(200, 50, 50);

    private JPanel  contentPanel;
    private JLabel  sectionTitle;   // ← standard JLabel, no custom paint

    public AdminDashboardGUI() {
        setTitle("RailMatrix — Admin Dashboard");
        setSize(1100, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(buildNavbar(),  BorderLayout.NORTH);
        add(buildSidebar(), BorderLayout.WEST);

        // ── Main content area ─────────────────────────────────────
        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(BG_LIGHT);

        // FIX: Use a plain JLabel — no custom paintComponent override
        sectionTitle = new JLabel("Dashboard Overview");
        sectionTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        sectionTitle.setForeground(PRIMARY_DARK);
        sectionTitle.setBorder(new EmptyBorder(20, 28, 10, 24));
        sectionTitle.setPreferredSize(new Dimension(900, 58));
        main.add(sectionTitle, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setBackground(BG_LIGHT);
        contentPanel.setLayout(new BorderLayout());

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_LIGHT);
        main.add(scroll, BorderLayout.CENTER);

        add(main, BorderLayout.CENTER);

        showOverview();
        setVisible(true);
    }

    // ────────────────────────────────────────────────────────────
    // NAVBAR
    // ────────────────────────────────────────────────────────────
    private JPanel buildNavbar() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(PRIMARY_DARK);
        nav.setPreferredSize(new Dimension(getWidth(), 55));
        nav.setBorder(new EmptyBorder(0, 20, 0, 20));

        // FIX: Plain JLabel for navbar title — no drawString override
        JLabel logo = new JLabel("🚆  RailMatrix Admin");
        logo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        logo.setForeground(WHITE);
        logo.setPreferredSize(new Dimension(260, 55));
        nav.add(logo, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        logoutBtn.setForeground(WHITE);
        logoutBtn.setBackground(new Color(180, 50, 50));
        logoutBtn.setBorder(new EmptyBorder(5, 14, 5, 14));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> {
            int c = JOptionPane.showConfirmDialog(this, "Logout?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (c == JOptionPane.YES_OPTION) { dispose(); new WelcomeScreen(); }
        });

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 12));
        right.setOpaque(false);
        right.add(logoutBtn);
        nav.add(right, BorderLayout.EAST);
        return nav;
    }

    // ────────────────────────────────────────────────────────────
    // SIDEBAR
    // ────────────────────────────────────────────────────────────
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(58, 42, 100));
        sidebar.setPreferredSize(new Dimension(200, getHeight()));
        sidebar.setBorder(new EmptyBorder(16, 0, 16, 0));

        sidebar.add(sidebarBtn("📊  Overview",        e -> showOverview()));
        sidebar.add(sidebarBtn("🚆  Manage Trains",   e -> showManageTrains()));
        sidebar.add(sidebarBtn("🚉  Manage Stations", e -> showManageStations()));
        sidebar.add(sidebarBtn("📋  All Bookings",    e -> showAllBookings()));
        sidebar.add(sidebarBtn("👥  All Users",       e -> showAllUsers()));
        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private JButton sidebarBtn(String text, ActionListener action) {
        JButton btn = new JButton(text);
        // FIX: Use Font.SANS_SERIF (always available) instead of "Helvetica Neue"
        btn.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(new EmptyBorder(12, 20, 12, 20));
        btn.setBackground(new Color(58, 42, 100));
        btn.setForeground(new Color(210, 200, 235));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);  // let Swing paint bg so hover works
        btn.setMaximumSize(new Dimension(200, 48));
        btn.setPreferredSize(new Dimension(200, 48));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(80, 60, 130)); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(new Color(58, 42, 100)); }
        });
        btn.addActionListener(action);
        return btn;
    }

    // ────────────────────────────────────────────────────────────
    // OVERVIEW
    // ────────────────────────────────────────────────────────────
    private void showOverview() {
        // FIX: just setText() — no reassignment of sectionTitle
        sectionTitle.setText("Dashboard Overview");
        contentPanel.removeAll();

        JPanel grid = new JPanel(new GridLayout(2, 3, 16, 16));
        grid.setOpaque(false);
        grid.setBorder(new EmptyBorder(10, 24, 24, 24));

        try {
            Connection con = DBConnection.getConnection();
            grid.add(statCard("Total Trains",     queryCount(con, "SELECT COUNT(*) FROM trains"),         "🚆", new Color(230, 220, 255)));
            grid.add(statCard("Total Stations",   queryCount(con, "SELECT COUNT(*) FROM stations"),       "🚉", new Color(220, 240, 255)));
            grid.add(statCard("Total Bookings",   queryCount(con, "SELECT COUNT(*) FROM bookings"),       "🎫", new Color(220, 255, 235)));
            grid.add(statCard("Total Users",      queryCount(con, "SELECT COUNT(*) FROM users"),          "👥", new Color(255, 240, 220)));
            grid.add(statCard("Today's Bookings", queryCount(con, "SELECT COUNT(*) FROM bookings WHERE DATE(booking_time) = CURDATE()"), "📅", new Color(255, 230, 230)));
            grid.add(statCard("Active Routes",    queryCount(con, "SELECT COUNT(*) FROM routes"),         "🗺", new Color(240, 230, 255)));
        } catch (Exception e) { e.printStackTrace(); }

        contentPanel.add(grid, BorderLayout.NORTH);
        refresh();
    }

    private JPanel statCard(String label, String value, String icon, Color bg) {
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 16, 16));
                g2.setColor(DIVIDER);
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 16, 16));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BorderLayout(0, 4));
        card.setPreferredSize(new Dimension(180, 120));
        card.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel iconLabel  = new JLabel(icon);
        iconLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        valueLabel.setForeground(PRIMARY_DARK);

        JLabel labelLabel = new JLabel(label);
        labelLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        labelLabel.setForeground(TEXT_GREY);

        JPanel textPanel = new JPanel(new GridLayout(3, 1, 0, 2));
        textPanel.setOpaque(false);
        textPanel.add(iconLabel);
        textPanel.add(valueLabel);
        textPanel.add(labelLabel);

        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    // ────────────────────────────────────────────────────────────
    // MANAGE TRAINS
    // ────────────────────────────────────────────────────────────
    private void showManageTrains() {
        sectionTitle.setText("Manage Trains");
        contentPanel.removeAll();

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 10));
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(0, 24, 0, 24));

        JButton addBtn = actionButton("+ Add Train", PRIMARY);
        addBtn.addActionListener(e -> showAddTrainDialog());
        top.add(addBtn);
        contentPanel.add(top, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(new EmptyBorder(0, 24, 24, 24));

        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT train_id, train_name, train_type, departure, destination, total_seats, base_fare FROM trains ORDER BY train_id";
            ResultSet rs = con.prepareStatement(query).executeQuery();

            String[] cols = {"ID", "Train Name", "Type", "From", "To", "Seats", "Base Fare"};
            java.util.List<Object[]> rows = new java.util.ArrayList<>();
            while (rs.next()) {
                rows.add(new Object[]{
                        rs.getInt("train_id"),
                        rs.getString("train_name"),
                        rs.getString("train_type"),
                        rs.getString("departure"),
                        rs.getString("destination"),
                        rs.getInt("total_seats"),
                        "₹" + String.format("%.0f", rs.getDouble("base_fare"))
                });
            }
            tablePanel.add(buildTable(cols, rows), BorderLayout.CENTER);
        } catch (Exception e) { e.printStackTrace(); }

        contentPanel.add(tablePanel, BorderLayout.CENTER);
        refresh();
    }

    // ────────────────────────────────────────────────────────────
    // MANAGE STATIONS
    // ────────────────────────────────────────────────────────────
    private void showManageStations() {
        sectionTitle.setText("Manage Stations");
        contentPanel.removeAll();

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 10));
        top.setOpaque(false);
        top.setBorder(new EmptyBorder(0, 24, 0, 24));

        JButton addBtn = actionButton("+ Add Station", PRIMARY);
        addBtn.addActionListener(e -> showAddStationDialog());
        top.add(addBtn);
        contentPanel.add(top, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(new EmptyBorder(0, 24, 24, 24));

        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.prepareStatement(
                "SELECT station_id, station_name, city FROM stations ORDER BY station_id"
            ).executeQuery();
            String[] cols = {"ID", "Station Name", "City"};
            java.util.List<Object[]> rows = new java.util.ArrayList<>();
            while (rs.next()) {
                rows.add(new Object[]{ rs.getInt(1), rs.getString(2), rs.getString(3) });
            }
            tablePanel.add(buildTable(cols, rows), BorderLayout.CENTER);
        } catch (Exception e) { e.printStackTrace(); }

        contentPanel.add(tablePanel, BorderLayout.CENTER);
        refresh();
    }

    // ────────────────────────────────────────────────────────────
    // ALL BOOKINGS
    // ────────────────────────────────────────────────────────────
    private void showAllBookings() {
        sectionTitle.setText("All Bookings");
        contentPanel.removeAll();

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(new EmptyBorder(10, 24, 24, 24));

        try {
            Connection con = DBConnection.getConnection();
            String query = """
                SELECT b.booking_id, b.pnr, u.name, t.train_name,
                       b.journey_date, b.total_passengers, b.status, b.booking_time
                FROM bookings b
                JOIN users u  ON u.user_id  = b.user_id
                JOIN trains t ON t.train_id = b.train_id
                ORDER BY b.booking_time DESC
            """;
            ResultSet rs = con.prepareStatement(query).executeQuery();
            String[] cols = {"Booking ID", "PNR", "User", "Train", "Journey Date", "Passengers", "Status", "Booked At"};
            java.util.List<Object[]> rows = new java.util.ArrayList<>();
            while (rs.next()) {
                rows.add(new Object[]{
                        rs.getInt("booking_id"),
                        rs.getString("pnr"),
                        rs.getString("name"),
                        rs.getString("train_name"),
                        rs.getDate("journey_date"),
                        rs.getInt("total_passengers"),
                        rs.getString("status"),
                        rs.getTimestamp("booking_time").toString().substring(0, 16)
                });
            }
            tablePanel.add(buildTable(cols, rows), BorderLayout.CENTER);
        } catch (Exception e) { e.printStackTrace(); }

        contentPanel.add(tablePanel, BorderLayout.CENTER);
        refresh();
    }

    // ────────────────────────────────────────────────────────────
    // ALL USERS
    // ────────────────────────────────────────────────────────────
    private void showAllUsers() {
        sectionTitle.setText("All Users");
        contentPanel.removeAll();

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(new EmptyBorder(10, 24, 24, 24));

        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.prepareStatement(
                "SELECT user_id, name, email, is_verified FROM users ORDER BY user_id"
            ).executeQuery();
            String[] cols = {"User ID", "Name", "Email", "Verified"};
            java.util.List<Object[]> rows = new java.util.ArrayList<>();
            while (rs.next()) {
                rows.add(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("is_verified") == 1 ? "✅ Yes" : "❌ No"
                });
            }
            tablePanel.add(buildTable(cols, rows), BorderLayout.CENTER);
        } catch (Exception e) { e.printStackTrace(); }

        contentPanel.add(tablePanel, BorderLayout.CENTER);
        refresh();
    }

    // ────────────────────────────────────────────────────────────
    // ADD TRAIN DIALOG
    // ────────────────────────────────────────────────────────────
    private void showAddTrainDialog() {
        JDialog dialog = new JDialog(this, "Add New Train", true);
        dialog.setSize(420, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(BG_LIGHT);

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(6, 16, 4, 16);
        gc.gridx = 0; gc.gridwidth = 2; gc.gridy = 0;

        JTextField nameField  = dialogField("Train Name");
        JTextField typeField  = dialogField("Type (e.g. Express)");
        JTextField depField   = dialogField("Departure Station");
        JTextField destField  = dialogField("Destination Station");
        JTextField seatsField = dialogField("Total Seats");
        JTextField fareField  = dialogField("Base Fare (₹)");
        JLabel statusLbl = new JLabel("", SwingConstants.CENTER);
        statusLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        for (JComponent c : new JComponent[]{
                label("Train Name"),  nameField,
                label("Train Type"),  typeField,
                label("Departure"),   depField,
                label("Destination"), destField,
                label("Total Seats"), seatsField,
                label("Base Fare"),   fareField,
                statusLbl
        }) { dialog.add(c, gc); gc.gridy++; }

        JButton saveBtn = actionButton("Save Train", PRIMARY);
        saveBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO trains(train_name,train_type,departure,destination,total_seats,base_fare,fare_per_km) VALUES(?,?,?,?,?,?,0.50)"
                );
                ps.setString(1, nameField.getText().trim());
                ps.setString(2, typeField.getText().trim());
                ps.setString(3, depField.getText().trim());
                ps.setString(4, destField.getText().trim());
                ps.setInt(5, Integer.parseInt(seatsField.getText().trim()));
                ps.setDouble(6, Double.parseDouble(fareField.getText().trim()));
                ps.executeUpdate();
                statusLbl.setForeground(GREEN);
                statusLbl.setText("Train added successfully!");
                Timer t = new Timer(1000, ev -> { dialog.dispose(); showManageTrains(); });
                t.setRepeats(false); t.start();
            } catch (Exception ex) {
                statusLbl.setForeground(ERROR_RED);
                statusLbl.setText("Error: " + ex.getMessage());
            }
        });
        gc.insets = new Insets(10, 16, 16, 16);
        dialog.add(saveBtn, gc);
        dialog.setVisible(true);
    }

    // ────────────────────────────────────────────────────────────
    // ADD STATION DIALOG
    // ────────────────────────────────────────────────────────────
    private void showAddStationDialog() {
        JDialog dialog = new JDialog(this, "Add New Station", true);
        dialog.setSize(380, 260);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        dialog.getContentPane().setBackground(BG_LIGHT);

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(6, 16, 4, 16);
        gc.gridx = 0; gc.gridwidth = 2; gc.gridy = 0;

        JTextField nameField = dialogField("Station Name");
        JTextField cityField = dialogField("City");
        JLabel statusLbl = new JLabel("", SwingConstants.CENTER);
        statusLbl.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        for (JComponent c : new JComponent[]{
                label("Station Name"), nameField,
                label("City"), cityField, statusLbl
        }) { dialog.add(c, gc); gc.gridy++; }

        JButton saveBtn = actionButton("Save Station", PRIMARY);
        saveBtn.addActionListener(e -> {
            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO stations(station_name, city) VALUES(?, ?)"
                );
                ps.setString(1, nameField.getText().trim());
                ps.setString(2, cityField.getText().trim());
                ps.executeUpdate();
                statusLbl.setForeground(GREEN);
                statusLbl.setText("Station added!");
                Timer t = new Timer(1000, ev -> { dialog.dispose(); showManageStations(); });
                t.setRepeats(false); t.start();
            } catch (Exception ex) {
                statusLbl.setForeground(ERROR_RED);
                statusLbl.setText("Error: " + ex.getMessage());
            }
        });
        gc.insets = new Insets(10, 16, 16, 16);
        dialog.add(saveBtn, gc);
        dialog.setVisible(true);
    }

    // ────────────────────────────────────────────────────────────
    // TABLE BUILDER
    // ────────────────────────────────────────────────────────────
    private JScrollPane buildTable(String[] cols, java.util.List<Object[]> rows) {
        Object[][] data = rows.toArray(new Object[0][]);
        JTable table = new JTable(data, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        table.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        table.setRowHeight(34);
        table.getTableHeader().setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(230, 220, 255));
        table.getTableHeader().setForeground(PRIMARY_DARK);
        table.setSelectionBackground(new Color(220, 210, 250));
        table.setGridColor(DIVIDER);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createLineBorder(DIVIDER));
        return sp;
    }

    // ────────────────────────────────────────────────────────────
    // UI HELPERS
    // ────────────────────────────────────────────────────────────
    private String queryCount(Connection con, String sql) {
        try {
            ResultSet rs = con.prepareStatement(sql).executeQuery();
            if (rs.next()) return String.valueOf(rs.getInt(1));
        } catch (Exception e) { e.printStackTrace(); }
        return "—";
    }

    private JButton actionButton(String text, Color bg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? PRIMARY_DARK : bg);
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
        btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        btn.setPreferredSize(new Dimension(160, 38));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JTextField dialogField(String placeholder) {
        JTextField f = new JTextField();
        f.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        f.setForeground(TEXT_GREY);
        f.setText(placeholder);
        f.setBorder(BorderFactory.createCompoundBorder(
                new RegisterGUI.RoundedBorder(10, new Color(200, 180, 220)),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        f.setPreferredSize(new Dimension(340, 36));
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

    private JLabel label(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        l.setForeground(PRIMARY);
        return l;
    }

    private void refresh() {
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
