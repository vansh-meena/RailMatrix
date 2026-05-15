package com.smartrail.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class DatePickerField extends JPanel {

    private static final Color PRIMARY      = new Color(72, 52, 120);
    private static final Color PRIMARY_DARK = new Color(50, 35, 90);
    private static final Color ACCENT       = new Color(155, 89, 182);
    private static final Color WHITE        = Color.WHITE;
    private static final Color TEXT_GREY    = new Color(100, 100, 100);
    private static final Color DIVIDER      = new Color(220, 210, 235);
    private static final Color TODAY_BG     = new Color(230, 220, 255);
    private static final Color SELECTED_BG  = new Color(72, 52, 120);
    private static final Color DISABLED_FG  = new Color(200, 200, 200);

    private final JTextField displayField;
    private LocalDate selectedDate = null;
    private final LocalDate minDate = LocalDate.now();
    private final LocalDate maxDate = LocalDate.now().plusDays(30);
    private final DateTimeFormatter displayFmt = DateTimeFormatter.ofPattern("dd MMM yyyy");
    private final DateTimeFormatter sqlFmt     = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Consumer<String> onDateSelected;

    public DatePickerField(String placeholder) {
        setLayout(new BorderLayout());
        setOpaque(false);

        displayField = new JTextField(placeholder) {
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
        displayField.setOpaque(false);
        displayField.setEditable(false);
        displayField.setFont(new Font("Helvetica Neue", Font.PLAIN, 13));
        displayField.setForeground(TEXT_GREY);
        displayField.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 36));
        displayField.setPreferredSize(new Dimension(200, 38));
        displayField.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Calendar icon label
        add(displayField, BorderLayout.CENTER);

        // Open picker on click
        MouseAdapter openPicker = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { showCalendarPopup(); }
        };
        displayField.addMouseListener(openPicker);
    }

    public void setOnDateSelected(Consumer<String> callback) {
        this.onDateSelected = callback;
    }

    // Returns date in YYYY-MM-DD format for DB queries
    public String getSelectedDateSQL() {
        return selectedDate != null ? selectedDate.format(sqlFmt) : null;
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    // ────────────────────────────────────────────────────────────
    // CALENDAR POPUP
    // ────────────────────────────────────────────────────────────
    private void showCalendarPopup() {
        JDialog popup = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), false);
        popup.setUndecorated(true);
        popup.setSize(300, 320);

        final LocalDate[] viewMonth = {minDate.withDayOfMonth(1)};

        JPanel calPanel = buildCalendarPanel(viewMonth, popup);
        popup.setContentPane(calPanel);

        // Position below the field
        Point loc = displayField.getLocationOnScreen();
        popup.setLocation(loc.x, loc.y + displayField.getHeight() + 4);

        // Close on outside click
        popup.addWindowFocusListener(new WindowAdapter() {
            public void windowLostFocus(WindowEvent e) { popup.dispose(); }
        });

        popup.setVisible(true);
    }

    private JPanel buildCalendarPanel(LocalDate[] viewMonth, JDialog popup) {
        JPanel panel = new JPanel(new BorderLayout()) {
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
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // ── Header ───────────────────────────────────────────────
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(0, 4, 8, 4));

        JButton prevBtn = arrowBtn("‹");
        JButton nextBtn = arrowBtn("›");

        JLabel monthLabel = new JLabel("", SwingConstants.CENTER);
        monthLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 14));
        monthLabel.setForeground(PRIMARY_DARK);

        header.add(prevBtn,    BorderLayout.WEST);
        header.add(monthLabel, BorderLayout.CENTER);
        header.add(nextBtn,    BorderLayout.EAST);
        panel.add(header, BorderLayout.NORTH);

        // ── Days grid ────────────────────────────────────────────
        JPanel gridWrapper = new JPanel(new BorderLayout());
        gridWrapper.setOpaque(false);

        // Day headers
        JPanel dayHeaders = new JPanel(new GridLayout(1, 7, 2, 2));
        dayHeaders.setOpaque(false);
        for (String d : new String[]{"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"}) {
            JLabel lbl = new JLabel(d, SwingConstants.CENTER);
            lbl.setFont(new Font("Helvetica Neue", Font.BOLD, 11));
            lbl.setForeground(ACCENT);
            dayHeaders.add(lbl);
        }
        gridWrapper.add(dayHeaders, BorderLayout.NORTH);

        JPanel daysGrid = new JPanel(new GridLayout(6, 7, 2, 2));
        daysGrid.setOpaque(false);
        gridWrapper.add(daysGrid, BorderLayout.CENTER);
        panel.add(gridWrapper, BorderLayout.CENTER);

        // Refresh calendar when month changes
        Runnable refresh = () -> {
            YearMonth ym = YearMonth.of(viewMonth[0].getYear(), viewMonth[0].getMonth());
            monthLabel.setText(ym.getMonth().name().substring(0,1)
                    + ym.getMonth().name().substring(1).toLowerCase()
                    + " " + ym.getYear());

            prevBtn.setEnabled(!ym.atDay(1).isBefore(minDate.withDayOfMonth(1))
                    && ym.atDay(1).isAfter(minDate.withDayOfMonth(1)));
            nextBtn.setEnabled(ym.atDay(1).isBefore(maxDate.withDayOfMonth(1)));

            daysGrid.removeAll();

            int firstDow = ym.atDay(1).getDayOfWeek().getValue() % 7;
            for (int i = 0; i < firstDow; i++) daysGrid.add(new JLabel(""));

            for (int day = 1; day <= ym.lengthOfMonth(); day++) {
                LocalDate date = ym.atDay(day);
                boolean inRange  = !date.isBefore(minDate) && !date.isAfter(maxDate);
                boolean isToday  = date.equals(LocalDate.now());
                boolean selected = date.equals(selectedDate);

                JButton dayBtn = new JButton(String.valueOf(day)) {
                    @Override protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        if (selected) {
                            g2.setColor(SELECTED_BG);
                            g2.fill(new RoundRectangle2D.Double(2, 2, getWidth()-4, getHeight()-4, 10, 10));
                            g2.setColor(WHITE);
                        } else if (isToday) {
                            g2.setColor(TODAY_BG);
                            g2.fill(new RoundRectangle2D.Double(2, 2, getWidth()-4, getHeight()-4, 10, 10));
                            g2.setColor(PRIMARY);
                        } else if (getModel().isRollover() && inRange) {
                            g2.setColor(new Color(240, 235, 255));
                            g2.fill(new RoundRectangle2D.Double(2, 2, getWidth()-4, getHeight()-4, 10, 10));
                            g2.setColor(PRIMARY);
                        } else {
                            g2.setColor(inRange ? PRIMARY_DARK : DISABLED_FG);
                        }
                        g2.setFont(getFont());
                        FontMetrics fm = g2.getFontMetrics();
                        g2.drawString(getText(),
                                (getWidth() - fm.stringWidth(getText())) / 2,
                                (getHeight() + fm.getAscent() - fm.getDescent()) / 2);
                        g2.dispose();
                    }
                };
                dayBtn.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
                dayBtn.setContentAreaFilled(false);
                dayBtn.setBorderPainted(false);
                dayBtn.setFocusPainted(false);
                dayBtn.setEnabled(inRange);
                dayBtn.setCursor(inRange
                        ? Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
                        : Cursor.getDefaultCursor());

                dayBtn.addActionListener(e -> {
                    selectedDate = date;
                    displayField.setText(date.format(displayFmt));
                    displayField.setForeground(PRIMARY_DARK);
                    if (onDateSelected != null) onDateSelected.accept(date.format(sqlFmt));
                    popup.dispose();
                });

                daysGrid.add(dayBtn);
            }

            daysGrid.revalidate();
            daysGrid.repaint();
        };

        prevBtn.addActionListener(e -> { viewMonth[0] = viewMonth[0].minusMonths(1); refresh.run(); });
        nextBtn.addActionListener(e -> { viewMonth[0] = viewMonth[0].plusMonths(1); refresh.run(); });

        refresh.run();
        return panel;
    }

    private JButton arrowBtn(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Helvetica Neue", Font.BOLD, 18));
        btn.setForeground(PRIMARY);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}