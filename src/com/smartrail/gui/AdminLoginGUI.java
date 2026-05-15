package com.smartrail.gui;

import com.smartrail.dao.AdminDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class AdminLoginGUI extends JFrame {

    private static final Color PRIMARY       = new Color(72, 52, 120);
    private static final Color PRIMARY_DARK  = new Color(50, 35, 90);
    private static final Color ACCENT        = new Color(155, 89, 182);
    private static final Color WHITE         = Color.WHITE;
    private static final Color TEXT_GREY     = new Color(100, 100, 100);
    private static final Color ERROR_RED     = new Color(200, 50, 50);
    private static final Color SUCCESS_GREEN = new Color(40, 160, 80);
    private static final Color BG_LIGHT      = new Color(245, 240, 255);

    private JTextField emailField;
    private JPasswordField passField;
    private JLabel statusLabel;
    private int attempts = 0;
    private long blockedUntil = 0;

    public AdminLoginGUI() {
        setTitle("RailMatrix — Admin Login");
        setSize(480, 460);
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
        root.setLayout(new GridBagLayout());
        setContentPane(root);

        JPanel card = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(WHITE);
                g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
                g2.setColor(new Color(180, 160, 210, 80));
                g2.draw(new RoundRectangle2D.Double(1, 1, getWidth()-2, getHeight()-2, 25, 25));
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new GridBagLayout());
        card.setPreferredSize(new Dimension(400, 390));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0; gc.gridwidth = 1;

        // Admin shield icon + logo
        JLabel logo = new JLabel("🛡 RailMatrix Admin", SwingConstants.CENTER);
        logo.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
        logo.setForeground(PRIMARY);
        gc.gridy = 0; gc.insets = new Insets(28, 24, 0, 24);
        card.add(logo, gc);

        JLabel title = new JLabel("Admin Portal", SwingConstants.CENTER);
        title.setFont(new Font("Helvetica Neue", Font.BOLD, 22));
        title.setForeground(PRIMARY_DARK);
        gc.gridy = 1; gc.insets = new Insets(4, 24, 2, 24);
        card.add(title, gc);

        JLabel sub = new JLabel("Restricted access — authorised personnel only", SwingConstants.CENTER);
        sub.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        sub.setForeground(TEXT_GREY);
        gc.gridy = 2; gc.insets = new Insets(0, 24, 20, 24);
        card.add(sub, gc);

        // Fields
        gc.insets = new Insets(6, 24, 2, 24);
        gc.gridy = 3; card.add(fieldLabel("Admin Email"), gc);
        emailField = (JTextField) roundedField("Enter admin email", false);
        gc.gridy = 4; card.add(emailField, gc);

        gc.gridy = 5; card.add(fieldLabel("Password"), gc);
        passField = (JPasswordField) roundedField("Enter password", true);
        gc.gridy = 6; card.add(passField, gc);

        // Status
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        gc.gridy = 7; gc.insets = new Insets(6, 24, 0, 24);
        card.add(statusLabel, gc);

        // Login button
        JButton loginBtn = roundedButton("Login as Admin", PRIMARY, WHITE);
        loginBtn.addActionListener(e -> handleLogin());
        gc.gridy = 8; gc.insets = new Insets(14, 24, 8, 24);
        card.add(loginBtn, gc);

        // Back button
        JButton backBtn = roundedButton("← Back to Home", new Color(240, 235, 255), PRIMARY);
        backBtn.addActionListener(e -> { dispose(); new WelcomeScreen(); });
        gc.gridy = 9; gc.insets = new Insets(0, 24, 24, 24);
        card.add(backBtn, gc);

        root.add(card);
        setVisible(true);
    }

    // ────────────────────────────────────────────────────────────
    // LOGIN HANDLER
    // ────────────────────────────────────────────────────────────
    private void handleLogin() {
        // Check block
        if (System.currentTimeMillis() < blockedUntil) {
            long remaining = (blockedUntil - System.currentTimeMillis()) / 1000 / 60;
            setStatus("Too many attempts. Try again in " + remaining + " min.", ERROR_RED);
            return;
        }

        String email = emailField.getText().trim();
        String pass  = new String(passField.getPassword());

        if (email.isEmpty() || email.equals("Enter admin email") ||
                pass.isEmpty()  || pass.equals("Enter password")) {
            setStatus("Please fill in all fields.", ERROR_RED); return;
        }

        if (AdminDAO.adminLogin(email, pass)) {
            attempts = 0;
            setStatus("Login successful! Loading dashboard...", SUCCESS_GREEN);
            Timer t = new Timer(900, e -> { dispose(); new AdminDashboardGUI(); });
            t.setRepeats(false);
            t.start();
        } else {
            attempts++;
            int remaining = 5 - attempts;
            if (attempts >= 5) {
                blockedUntil = System.currentTimeMillis() + (10 * 60 * 1000);
                setStatus("Too many failed attempts. Blocked for 10 minutes.", ERROR_RED);
            } else {
                setStatus("Invalid credentials. " + remaining + " attempt(s) left.", ERROR_RED);
            }
        }
    }

    private void setStatus(String msg, Color color) {
        statusLabel.setText(msg);
        statusLabel.setForeground(color);
    }

    // ── UI helpers (same pattern as LoginGUI) ────────────────────
    private JLabel fieldLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Helvetica Neue", Font.BOLD, 12));
        lbl.setForeground(PRIMARY);
        return lbl;
    }

    private JTextField roundedField(String placeholder, boolean isPassword) {
        JTextField field = isPassword ? new JPasswordField(20) : new JTextField(20);
        field.setOpaque(true);
        field.setBackground(WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                new RegisterGUI.RoundedBorder(15, new Color(200, 180, 220)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
        field.setForeground(TEXT_GREY);
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
                    if (field.getText().equals(placeholder)) { field.setText(""); field.setForeground(Color.BLACK); }
                }
                public void focusLost(FocusEvent e) {
                    if (field.getText().isEmpty()) { field.setText(placeholder); field.setForeground(TEXT_GREY); }
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
                g2.drawString(getText(),
                        (getWidth() - fm.stringWidth(getText())) / 2,
                        (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
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
}