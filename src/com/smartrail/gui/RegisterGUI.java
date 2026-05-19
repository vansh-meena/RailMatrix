package com.smartrail.gui;

import com.smartrail.dao.UserDAO;
import com.smartrail.model.User;
import com.smartrail.util.EmailService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class RegisterGUI extends JFrame {

    // ── Colour palette (same as WelcomeScreen) ───────────────────
    private static final Color PRIMARY       = new Color(72, 52, 120);
    private static final Color PRIMARY_DARK  = new Color(50, 35, 90);
    private static final Color ACCENT        = new Color(155, 89, 182);
    private static final Color WHITE         = Color.WHITE;
    private static final Color TEXT_GREY     = new Color(100, 100, 100);
    private static final Color ERROR_RED     = new Color(200, 50, 50);
    private static final Color SUCCESS_GREEN = new Color(40, 160, 80);
    private static final Color WARN_ORANGE   = new Color(200, 120, 0);
    private static final Color BG_LIGHT      = new Color(245, 240, 255);
    private static final Color RULE_MET      = new Color(40, 160, 80);
    private static final Color RULE_UNMET    = new Color(180, 180, 180);

    private JTextField    nameField, emailField;
    private JPasswordField passField, confirmPassField;
    private JLabel statusLabel;

    // Password rule labels
    private JLabel ruleLength, ruleUpper, ruleNumber, ruleSpecial;

    public RegisterGUI() {
        setTitle("RailMatrix — Register");
        setSize(480, 660);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // ── Root gradient background ──────────────────────────────
        JPanel root = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setPaint(new GradientPaint(0, 0, BG_LIGHT, 0, getHeight(), new Color(225, 210, 255)));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setLayout(new GridBagLayout());
        setContentPane(root);

        // Scrollable wrapper so content doesn't get cut off
        JPanel card = buildCard();
        JScrollPane scroll = new JScrollPane(card);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setPreferredSize(new Dimension(460, 620));

        root.add(scroll);
        setVisible(true);
    }

    private JPanel buildCard() {
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
        card.setPreferredSize(new Dimension(420, 620));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(6, 24, 2, 24);
        gc.gridx = 0; gc.gridwidth = 1;

        // ── Logo ──────────────────────────────────────────────────
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
        gc.gridy = 2; gc.insets = new Insets(0, 24, 14, 24);
        card.add(sub, gc);

        gc.insets = new Insets(4, 24, 2, 24);

        // ── Full Name ─────────────────────────────────────────────
        gc.gridy = 3; card.add(fieldLabel("Full Name"), gc);
        nameField = roundedField("Enter your full name", false);
        gc.gridy = 4; card.add(nameField, gc);

        // ── Email ─────────────────────────────────────────────────
        gc.gridy = 5; card.add(fieldLabel("Email Address"), gc);
        emailField = roundedField("Enter your email", false);
        gc.gridy = 6; card.add(emailField, gc);

        // ── Password ──────────────────────────────────────────────
        gc.gridy = 7; card.add(fieldLabel("Password"), gc);
        passField = (JPasswordField) roundedField("Create a password", true);
        gc.gridy = 8; card.add(passField, gc);

        // ── Password Strength Panel ───────────────────────────────
        gc.gridy = 9; gc.insets = new Insets(6, 24, 2, 24);
        card.add(buildStrengthPanel(), gc);

        // ── Confirm Password ──────────────────────────────────────
        gc.gridy = 10; gc.insets = new Insets(4, 24, 2, 24);
        card.add(fieldLabel("Confirm Password"), gc);
        confirmPassField = (JPasswordField) roundedField("Repeat your password", true);
        gc.gridy = 11; card.add(confirmPassField, gc);

        // ── Status Label ──────────────────────────────────────────
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        gc.gridy = 12; gc.insets = new Insets(6, 24, 0, 24);
        card.add(statusLabel, gc);

        // ── Register Button ───────────────────────────────────────
        JButton registerBtn = roundedButton("Register", PRIMARY, WHITE);
        registerBtn.addActionListener(e -> handleRegister());
        gc.gridy = 13; gc.insets = new Insets(10, 24, 8, 24);
        card.add(registerBtn, gc);

        // ── Login / Back links ────────────────────────────────────
        JPanel loginRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 0));
        loginRow.setOpaque(false);
        JLabel already = new JLabel("Already have an account?");
        already.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        already.setForeground(TEXT_GREY);
        JButton loginLink = linkButton("Login here");
        loginLink.addActionListener(e -> { dispose(); new LoginGUI(); });
        loginRow.add(already);
        loginRow.add(loginLink);
        gc.gridy = 14; gc.insets = new Insets(0, 24, 4, 24);
        card.add(loginRow, gc);

        JButton backBtn = roundedButton("← Back to Home", new Color(240, 235, 255), PRIMARY);
        backBtn.addActionListener(e -> { dispose(); new WelcomeScreen(); });
        gc.gridy = 15; gc.insets = new Insets(0, 24, 24, 24);
        card.add(backBtn, gc);

        // Attach live password listener
        passField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e)  { updateStrength(); }
            public void removeUpdate(DocumentEvent e)  { updateStrength(); }
            public void changedUpdate(DocumentEvent e) { updateStrength(); }
        });

        return card;
    }

    // ─────────────────────────────────────────────────────────────
    // PASSWORD STRENGTH PANEL
    // ─────────────────────────────────────────────────────────────
    private JPanel buildStrengthPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10, new Color(220, 200, 240)),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        JLabel heading = new JLabel("Password must contain:");
        heading.setFont(new Font("Helvetica Neue", Font.BOLD, 11));
        heading.setForeground(PRIMARY);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(heading);
        panel.add(Box.createVerticalStrut(4));

        ruleLength  = ruleLabel("✗  At least 8 characters");
        ruleUpper   = ruleLabel("✗  At least 1 uppercase letter (A-Z)");
        ruleNumber  = ruleLabel("✗  At least 1 number (0-9)");
        ruleSpecial = ruleLabel("✗  At least 1 special character (@, #, !, etc.)");

        panel.add(ruleLength);
        panel.add(ruleUpper);
        panel.add(ruleNumber);
        panel.add(ruleSpecial);

        return panel;
    }

    private JLabel ruleLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Helvetica Neue", Font.PLAIN, 11));
        lbl.setForeground(RULE_UNMET);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private void updateStrength() {
        String pass = new String(passField.getPassword());
        // Skip if showing placeholder
        if (pass.equals("Create a password")) return;

        setRule(ruleLength,  pass.length() >= 8,          "At least 8 characters");
        setRule(ruleUpper,   pass.matches(".*[A-Z].*"),    "At least 1 uppercase letter (A-Z)");
        setRule(ruleNumber,  pass.matches(".*[0-9].*"),    "At least 1 number (0-9)");
        setRule(ruleSpecial, pass.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*"),
                "At least 1 special character (@, #, !, etc.)");
    }

    private void setRule(JLabel lbl, boolean met, String text) {
        lbl.setText((met ? "✓  " : "✗  ") + text);
        lbl.setForeground(met ? RULE_MET : RULE_UNMET);
    }

    private boolean allRulesMet(String pass) {
        return pass.length() >= 8
            && pass.matches(".*[A-Z].*")
            && pass.matches(".*[0-9].*")
            && pass.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }

    // ─────────────────────────────────────────────────────────────
    // REGISTER HANDLER
    // ─────────────────────────────────────────────────────────────
    private void handleRegister() {
        String name    = nameField.getText().trim();
        String email   = emailField.getText().trim();
        String pass    = new String(passField.getPassword());
        String confirm = new String(confirmPassField.getPassword());

        // ── Basic validation ──────────────────────────────────────
        if (name.isEmpty() || name.equals("Enter your full name") ||
            email.isEmpty() || email.equals("Enter your email") ||
            pass.isEmpty()  || pass.equals("Create a password") ||
            confirm.isEmpty()|| confirm.equals("Repeat your password")) {
            setStatus("All fields are required.", ERROR_RED); return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            setStatus("Please enter a valid email address.", ERROR_RED); return;
        }

        // ── Password strength ─────────────────────────────────────
        if (!allRulesMet(pass)) {
            setStatus("Password does not meet all requirements above.", ERROR_RED); return;
        }
        if (!pass.equals(confirm)) {
            setStatus("Passwords do not match.", ERROR_RED); return;
        }

        // ── Check existing ────────────────────────────────────────
        UserDAO userDAO = new UserDAO();
        if (userDAO.isUserExists(email)) {
            setStatus("Email already registered. Try logging in.", ERROR_RED); return;
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(pass);

        // ── Register and send verification email ──────────────────
        String token = userDAO.registerWithVerification(user);
        if (token == null) {
            setStatus("Registration failed. Please try again.", ERROR_RED); return;
        }

        // Send verification email (in background thread so UI doesn't freeze)
        setStatus("Sending verification email...", WARN_ORANGE);
        new Thread(() -> {
            try {
                EmailService.sendVerificationEmail(email, name, token);
                SwingUtilities.invokeLater(() ->
                    setStatus("✓ Check your email to verify your account!", SUCCESS_GREEN));
            } catch (Exception ex) {
                ex.printStackTrace();
                SwingUtilities.invokeLater(() ->
                    setStatus("Registered! (Email sending failed — contact support)", WARN_ORANGE));
            }
            SwingUtilities.invokeLater(() -> {
                Timer t = new Timer(3000, e -> { dispose(); new LoginGUI(); });
                t.setRepeats(false);
                t.start();
            });
        }).start();
    }

    private void setStatus(String msg, Color color) {
        statusLabel.setText(msg);
        statusLabel.setForeground(color);
    }

    // ── UI HELPERS ────────────────────────────────────────────────
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
                g2.drawString(getText(), (getWidth() - fm.stringWidth(getText())) / 2,
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

    // ── Custom rounded border (used by other classes too) ─────────
    public static class RoundedBorder implements Border {
        private final int radius;
        private final Color color;
        public RoundedBorder(int radius, Color color) { this.radius = radius; this.color = color; }
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