package com.smartrail.gui;

import com.smartrail.dao.TrainDAO;
import com.smartrail.util.DBConnection;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;

public class WelcomeScreen extends JFrame {

    // ── Colour palette ──────────────────────────────────────────
    private static final Color PRIMARY      = new Color(72, 52, 120);   // deep purple
    private static final Color PRIMARY_DARK = new Color(50, 35, 90);    // navbar / hover
    private static final Color ACCENT       = new Color(155, 89, 182);  // light purple
    private static final Color WHITE        = Color.WHITE;
    private static final Color TEXT_GREY    = new Color(100, 100, 100);
    private static final Color BG_OVERLAY   = new Color(30, 20, 60, 160); // semi-transparent

    private BufferedImage bgImage;
    private JTextField fromField, toField, dateField;

    public WelcomeScreen() {

        // ── Startup: generate schedules ─────────────────────────
        new Thread(() -> {
            try {
                Connection con = DBConnection.getConnection();
                TrainDAO trainDAO = new TrainDAO(con);
                for (int i = 1; i <= 30; i++) trainDAO.generateUpcomingSchedules(i);
            } catch (Exception e) { e.printStackTrace(); }
        }).start();

        // ── Load background image ────────────────────────────────
        try {
            URL imgUrl = getClass().getClassLoader().getResource("images/background.jpg");
            if (imgUrl != null) bgImage = ImageIO.read(imgUrl);
        } catch (IOException e) { e.printStackTrace(); }

        setTitle("RailMatrix — Book Train Tickets");
        setSize(1100, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ── NAVBAR ───────────────────────────────────────────────
        add(buildNavbar(), BorderLayout.NORTH);

        // ── MAIN CONTENT (background panel) ─────────────────────
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                if (bgImage != null) {
                    g2.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                } else {
                    // fallback gradient
                    GradientPaint gp = new GradientPaint(0, 0, PRIMARY_DARK, getWidth(), getHeight(), ACCENT);
                    g2.setPaint(gp);
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
                // dark overlay for readability
                g2.setColor(BG_OVERLAY);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setOpaque(false);

        // ── SEARCH CARD ──────────────────────────────────────────
        JPanel card = buildSearchCard();
        mainPanel.add(card);
        add(mainPanel, BorderLayout.CENTER);

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

        // Nav buttons (center)
        JPanel navLinks = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 12));
        navLinks.setOpaque(false);
        navLinks.add(navButton("Home"));
        navLinks.add(navButton("Trains"));
        navLinks.add(navButton("Login", e -> { dispose(); new LoginGUI(); }));
        navLinks.add(navButton("Admin", e -> { dispose(); new AdminLoginGUI(); }));
        navLinks.add(navButton("Register", e -> { dispose(); new RegisterGUI(); }));
        navLinks.add(navButton("Exit", e -> System.exit(0)));
        nav.add(navLinks, BorderLayout.CENTER);

        // Person icon (right)
        JButton profileBtn = new JButton("👤");
        profileBtn.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        profileBtn.setForeground(WHITE);
        profileBtn.setBackground(PRIMARY_DARK);
        profileBtn.setBorderPainted(false);
        profileBtn.setFocusPainted(false);
        profileBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        profileBtn.setToolTipText("Not logged in");
        profileBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this,
                        "You are not logged in.\nPlease login or register.",
                        "Profile", JOptionPane.INFORMATION_MESSAGE)
        );

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 12));
        rightPanel.setOpaque(false);
        rightPanel.add(profileBtn);
        nav.add(rightPanel, BorderLayout.EAST);

        return nav;
    }

    private JButton navButton(String text) {
        return navButton(text, null);
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
        card.setPreferredSize(new Dimension(750, 280));

        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 12, 8, 12);
        gc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("Find Your Train", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(PRIMARY);
        gc.gridx = 0; gc.gridy = 0; gc.gridwidth = 3;
        card.add(title, gc);

        // Subtitle
        JLabel sub = new JLabel("Search trains, check availability and book instantly", SwingConstants.CENTER);
        sub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        sub.setForeground(TEXT_GREY);
        gc.gridy = 1;
        card.add(sub, gc);

        // Field labels row
        gc.gridwidth = 1; gc.gridy = 2;
        gc.gridx = 0; card.add(fieldLabel("From"), gc);
        gc.gridx = 1; card.add(fieldLabel("To"), gc);
        gc.gridx = 2; card.add(fieldLabel("Journey Date"), gc);

        // Input fields row
        fromField = roundedField("e.g. New Delhi Junction");
        toField   = roundedField("e.g. Mumbai Central");
        dateField = roundedField("YYYY-MM-DD");

        gc.gridy = 3;
        gc.gridx = 0; card.add(fromField, gc);
        gc.gridx = 1; card.add(toField, gc);
        gc.gridx = 2; card.add(dateField, gc);

        // Search button
        JButton searchBtn = new JButton("Search Trains") {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? PRIMARY_DARK : PRIMARY);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(WHITE);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        searchBtn.setPreferredSize(new Dimension(200, 42));
        searchBtn.setContentAreaFilled(false);
        searchBtn.setBorderPainted(false);
        searchBtn.setFocusPainted(false);
        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.addActionListener(e -> handleSearch());

        gc.gridy = 4; gc.gridx = 0; gc.gridwidth = 3;
        gc.fill = GridBagConstraints.NONE;
        gc.anchor = GridBagConstraints.CENTER;
        gc.insets = new Insets(16, 12, 8, 12);
        card.add(searchBtn, gc);

        return card;
    }

    private JLabel fieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(PRIMARY);
        return lbl;
    }

    private JTextField roundedField(String placeholder) {
        JTextField field = new JTextField(16) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WHITE);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
                g2.setColor(new Color(200, 180, 220));
                g2.draw(new RoundRectangle2D.Double(0, 0, getWidth()-1, getHeight()-1, 15, 15));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setOpaque(false);
        field.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT_GREY);
        field.setText(placeholder);
        field.setPreferredSize(new Dimension(200, 38));

        // Placeholder behaviour
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

    // ────────────────────────────────────────────────────────────
    // SEARCH HANDLER
    // ────────────────────────────────────────────────────────────
    private void handleSearch() {
        String from = fromField.getText().trim();
        String to   = toField.getText().trim();
        String date = dateField.getText().trim();

        if (from.isEmpty() || from.equals("e.g. New Delhi Junction") ||
                to.isEmpty()   || to.equals("e.g. Mumbai Central")       ||
                date.isEmpty() || date.equals("YYYY-MM-DD")) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all fields.", "Missing Info",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Date journeyDate = Date.valueOf(date);
            new SearchResultGUI(from, to, journeyDate);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Invalid date format. Use YYYY-MM-DD.", "Date Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WelcomeScreen::new);
    }
}