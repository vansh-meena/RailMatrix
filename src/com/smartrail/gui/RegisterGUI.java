package com.smartrail.gui;

import com.smartrail.dao.UserDAO;
import com.smartrail.model.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class RegisterGUI extends JFrame {

    // ── Colour palette (same as WelcomeScreen) ───────────────────
    private static final Color PRIMARY      = new Color(72, 52, 120);
    private static final Color PRIMARY_DARK = new Color(50, 35, 90);
    private static final Color ACCENT       = new Color(155, 89, 182);
    private static final Color WHITE        = Color.WHITE;
    private static final Color TEXT_GREY    = new Color(100, 100, 100);
    private static final Color ERROR_RED    = new Color(200, 50, 50);
    private static final Color SUCCESS_GREEN= new Color(40, 160, 80);
    private static final Color BG_LIGHT     = new Color(245, 240, 255);

    private JTextField nameField, emailField;
    private JPasswordField passField, confirmPassField;
    private JLabel statusLabel;

    public RegisterGUI() {
        setTitle("RailMatrix — Register");
        setSize(480, 580);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Root panel with gradient background ──────────────────
        JPanel root = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, BG_LIGHT, 0, getHeight(), new Color(225, 210, 255));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setLayout(new GridBagLayout());
        setContentPane(root);

        // ── Card ─────────────────────────────────────────────────
        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WHITE);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
                // subtle shadow effect via border
                g2.setColor(new Color(180, 160, 210, 80));
                g2.draw(new RoundRectangle2D.Double(1, 1, getWidth()-2, getHeight()-2, 25, 25));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(400, 480));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(8, 24, 4, 24);
        gc.gridx = 0; gc.gridwidth = 1;

        // Logo + Title
        JLabel logo = new JLabel("🚆 RailMatrix", SwingConstants.CENTER);
        logo.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
        logo.setForeground(PRIMARY);
        gc.gridy = 0; gc.insets = new Insets(28, 24, 0, 24);
        card.add(logo, gc);

        JLabel title = new JLabel("Create an Account", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
        title.setForeground(PRIMARY_DARK);
        gc.gridy = 1; gc.insets = new Insets(4, 24, 4, 24);
        card.add(title, gc);

        JLabel sub = new JLabel("Join RailMatrix and book train tickets easily", SwingConstants.CENTER);
        sub.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        sub.setForeground(TEXT_GREY);
        gc.gridy = 2; gc.insets = new Insets(0, 24, 16, 24);
        card.add(sub, gc);

        // Fields
        gc.insets = new Insets(6, 24, 2, 24);

        gc.gridy = 3; card.add(fieldLabel("Full Name"), gc);
        nameField = roundedField("Enter your full name", false);
        gc.gridy = 4; card.add(nameField, gc);

        gc.gridy = 5; card.add(fieldLabel("Email Address"), gc);
        emailField = roundedField("Enter your email", false);
        gc.gridy = 6; card.add(emailField, gc);

        gc.gridy = 7; card.add(fieldLabel("Password"), gc);
        passField = (JPasswordField) roundedField("Create a password", true);
        gc.gridy = 8; card.add(passField, gc);

        gc.gridy = 9; card.add(fieldLabel("Confirm Password"), gc);
        confirmPassField = (JPasswordField) roundedField("Repeat your password", true);
        gc.gridy = 10; card.add(confirmPassField, gc);

        // Status label
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        gc.gridy = 11; gc.insets = new Insets(6, 24, 0, 24);
        card.add(statusLabel, gc);

        // Register button
        JButton registerBtn = roundedButton("Register", PRIMARY, WHITE);
        registerBtn.addActionListener(e -> handleRegister());
        gc.gridy = 12; gc.insets = new Insets(12, 24, 8, 24);
        card.add(registerBtn, gc);

        // Back to login
        JPanel loginRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        loginRow.setOpaque(false);
        JLabel already = new JLabel("Already have an account?");
        already.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        already.setForeground(TEXT_GREY);
        JButton loginLink = linkButton("Login here");
        loginLink.addActionListener(e -> { dispose(); new LoginGUI(); });
        loginRow.add(already);
        loginRow.add(loginLink);
        gc.gridy = 13; gc.insets = new Insets(0, 24, 24, 24);
        JButton backBtn = roundedButton("← Back to Home", new Color(240, 235, 255), PRIMARY);
        backBtn.addActionListener(e -> { dispose(); new WelcomeScreen(); });
        gc.gridy = 14; gc.insets = new Insets(0, 24, 24, 24);
        card.add(backBtn, gc);
        card.add(loginRow, gc);

        root.add(card);
        setVisible(true);
    }

    // ────────────────────────────────────────────────────────────
    // REGISTER HANDLER
    // ────────────────────────────────────────────────────────────
    private void handleRegister() {
        String name     = nameField.getText().trim();
        String email    = emailField.getText().trim();
        String pass     = new String(passField.getPassword());
        String confirm  = new String(confirmPassField.getPassword());

        // Validation
        if (name.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            setStatus("All fields are required.", ERROR_RED); return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            setStatus("Please enter a valid email.", ERROR_RED); return;
        }
        if (pass.length() < 6) {
            setStatus("Password must be at least 6 characters.", ERROR_RED); return;
        }
        if (!pass.equals(confirm)) {
            setStatus("Passwords do not match.", ERROR_RED); return;
        }

        UserDAO userDAO = new UserDAO();

        if (userDAO.isUserExists(email)) {
            setStatus("Email already registered. Try logging in.", ERROR_RED); return;
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(pass);

        if (userDAO.register(user)) {
            setStatus("Registration successful! Redirecting...", SUCCESS_GREEN);
            Timer t = new Timer(1200, e -> { dispose(); new LoginGUI(); });
            t.setRepeats(false);
            t.start();
        } else {
            setStatus("Registration failed. Please try again.", ERROR_RED);
        }
    }

    private void setStatus(String msg, Color color) {
        statusLabel.setText(msg);
        statusLabel.setForeground(color);
    }

    // ────────────────────────────────────────────────────────────
    // UI HELPERS
    // ────────────────────────────────────────────────────────────
    private JLabel fieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
        lbl.setForeground(PRIMARY);
        return lbl;
    }

    private JTextField roundedField(String placeholder, boolean isPassword) {
        JTextField field = isPassword ? new JPasswordField(20) : new JTextField(20);

        field.setOpaque(false);
        field.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(15, new Color(200, 180, 220)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
        field.setForeground(TEXT_GREY);
        field.setBackground(WHITE);
        field.setPreferredSize(new Dimension(340, 40));

        if (isPassword) {
            ((JPasswordField) field).setEchoChar((char) 0);
            field.setText(placeholder);
            field.addFocusListener(new FocusAdapter() {
                public void focusGained(FocusEvent e) {
                    if (new String(((JPasswordField) field).getPassword()).equals(placeholder)) {
                        field.setText("");
                        field.setForeground(Color.BLACK);
                        ((JPasswordField) field).setEchoChar('●');
                    }
                }
                public void focusLost(FocusEvent e) {
                    if (new String(((JPasswordField) field).getPassword()).isEmpty()) {
                        ((JPasswordField) field).setEchoChar((char) 0);
                        field.setText(placeholder);
                        field.setForeground(TEXT_GREY);
                    }
                }
            });
        } else {
            field.setText(placeholder);
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
        }
        return field;
    }

    private JButton roundedButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? PRIMARY_DARK : bg);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
                g2.setColor(fg);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        btn.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(340, 42));
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private JButton linkButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
        btn.setForeground(ACCENT);
        btn.setBackground(null);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setForeground(PRIMARY); }
            public void mouseExited(MouseEvent e)  { btn.setForeground(ACCENT);  }
        });
        return btn;
    }

    // ── Custom rounded border ────────────────────────────────────
    static class RoundedBorder implements Border {
        private final int radius;
        private final Color color;
        RoundedBorder(int radius, Color color) { this.radius = radius; this.color = color; }
        public Insets getBorderInsets(Component c) { return new Insets(4, 8, 4, 8); }
        public boolean isBorderOpaque() { return false; }
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Double(x, y, w-1, h-1, radius, radius));
            g2.dispose();
        }
    }
}