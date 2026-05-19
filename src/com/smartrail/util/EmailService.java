package com.smartrail.util;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailService {

    // ── Gmail SMTP config ────────────────────────────────────────
    private static final String FROM_EMAIL    = "vansh.23bcon1705@jecrcu.edu.in"; // replace
    private static final String FROM_PASSWORD = "webc baab jzvk ykpp";    // replace (App Password, not Gmail password)
    private static final String FROM_NAME     = "RailMatrix Bookings";

    private static Session createSession() {
        Properties props = new Properties();
        props.put("mail.smtp.auth",                   "true");
        props.put("mail.smtp.host",                   "smtp.gmail.com");
        props.put("mail.smtp.port",                   "465");
        props.put("mail.smtp.socketFactory.port",     "465");
        props.put("mail.smtp.socketFactory.class",    "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.ssl.enable",             "true");
        props.put("mail.smtp.connectiontimeout",      "10000");
        props.put("mail.smtp.timeout",               "10000");

        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(FROM_EMAIL, FROM_PASSWORD);
            }
        });
    }

    // ────────────────────────────────────────────────────────────
    // BOOKING CONFIRMATION EMAIL
    // ────────────────────────────────────────────────────────────
    public static void sendBookingConfirmation(String toEmail, String userName,
                                               int bookingId, String trainName,
                                               String departure, String destination,
                                               String journeyDate, int passengers,
                                               double totalFare, String paymentMethod) {
        try {
            Session session = createSession();
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject("Booking Confirmed! #" + bookingId + " — " + trainName);

            String body = buildBookingEmailHTML(userName, bookingId, trainName,
                    departure, destination, journeyDate, passengers, totalFare, paymentMethod);

            msg.setContent(body, "text/html; charset=utf-8");
            Transport.send(msg);

            System.out.println("Booking confirmation email sent to " + toEmail);

        } catch (Exception e) {
            System.err.println("Failed to send booking email: " + e.getMessage());
            // Don't crash the app if email fails
        }
    }

    // ────────────────────────────────────────────────────────────
    // CANCELLATION EMAIL
    // ────────────────────────────────────────────────────────────
    public static void sendCancellationConfirmation(String toEmail, String userName,
                                                    int bookingId, String trainName,
                                                    String journeyDate, double refundAmount) {
        try {
            Session session = createSession();
            Message msg = new MimeMessage(session);

            msg.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject("Booking Cancelled #" + bookingId + " — Refund: ₹" + String.format("%.0f", refundAmount));

            String body = buildCancellationEmailHTML(userName, bookingId, trainName,
                    journeyDate, refundAmount);

            msg.setContent(body, "text/html; charset=utf-8");
            Transport.send(msg);

            System.out.println("Cancellation email sent to " + toEmail);

        } catch (Exception e) {
            System.err.println("Failed to send cancellation email: " + e.getMessage());
        }
    }

    // ────────────────────────────────────────────────────────────
    // EMAIL VERIFICATION
    // ────────────────────────────────────────────────────────────
    public static void sendVerificationEmail(String toEmail, String userName, String token) {
        // The verify link points to the Node.js API (update host when deployed)
        String verifyLink = "http://localhost:3000/api/auth/verify-email?token=" + token;
        try {
            Session session = createSession();
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(FROM_EMAIL, FROM_NAME));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            msg.setSubject("\uD83D\uDE86 Verify your RailMatrix email");

            String body = """
                <!DOCTYPE html>
                <html><head><meta charset="UTF-8"><style>
                    body{font-family:'Helvetica Neue',Arial,sans-serif;background:#f5f0ff;margin:0;padding:20px}
                    .c{max-width:560px;margin:0 auto;background:white;border-radius:16px;overflow:hidden;box-shadow:0 4px 20px rgba(72,52,120,.15)}
                    .h{background:linear-gradient(135deg,#32235a,#483478);padding:28px 32px}
                    .h h1{color:white;margin:0;font-size:22px}.h p{color:#c8b8e8;margin:4px 0 0;font-size:14px}
                    .b{padding:28px 32px}
                    .btn{display:inline-block;background:#483478;color:white;text-decoration:none;
                         padding:14px 32px;border-radius:10px;font-weight:bold;font-size:15px;margin:20px 0}
                    .note{color:#888;font-size:12px;margin-top:16px}
                    .f{background:#f8f4ff;padding:16px 32px;text-align:center;color:#aaa;font-size:12px}
                </style></head>
                <body><div class="c">
                    <div class="h"><h1>\uD83D\uDE86 RailMatrix</h1><p>Verify your email address</p></div>
                    <div class="b">
                        <p>Hello <strong>%s</strong>,<br>Thanks for registering! Click below to verify your email.</p>
                        <a href="%s" class="btn">\u2705 Verify My Email</a>
                        <p class="note">This link expires in 24 hours. If you didn't register, ignore this email.</p>
                        <p class="note">Or paste in browser:<br>%s</p>
                    </div>
                    <div class="f">© 2026 RailMatrix · Do not reply to this email.</div>
                </div></body></html>
                """.formatted(userName, verifyLink, verifyLink);

            msg.setContent(body, "text/html; charset=utf-8");
            Transport.send(msg);
            System.out.println("Verification email sent to " + toEmail);
        } catch (Exception e) {
            System.err.println("Failed to send verification email: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // ────────────────────────────────────────────────────────────
    // BOOKING EMAIL HTML TEMPLATE
    // ────────────────────────────────────────────────────────────
    private static String buildBookingEmailHTML(String userName, int bookingId,
                                                String trainName, String departure,
                                                String destination, String journeyDate,
                                                int passengers, double totalFare,
                                                String paymentMethod) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: 'Helvetica Neue', Arial, sans-serif; background: #f5f0ff; margin: 0; padding: 20px; }
                    .container { max-width: 560px; margin: 0 auto; background: white; border-radius: 16px; overflow: hidden; box-shadow: 0 4px 20px rgba(72,52,120,0.15); }
                    .header { background: linear-gradient(135deg, #32235a, #483478); padding: 28px 32px; }
                    .header h1 { color: white; margin: 0; font-size: 22px; }
                    .header p { color: #c8b8e8; margin: 4px 0 0; font-size: 14px; }
                    .body { padding: 28px 32px; }
                    .greeting { font-size: 16px; color: #32235a; margin-bottom: 20px; }
                    .ticket { background: #f8f4ff; border: 1px solid #dcd0ed; border-radius: 12px; padding: 20px; margin-bottom: 20px; }
                    .ticket-row { display: flex; justify-content: space-between; padding: 6px 0; border-bottom: 1px solid #ede5ff; }
                    .ticket-row:last-child { border-bottom: none; }
                    .ticket-label { color: #888; font-size: 13px; }
                    .ticket-value { color: #32235a; font-size: 13px; font-weight: bold; }
                    .route { text-align: center; padding: 16px 0; }
                    .route .from, .route .to { font-size: 18px; font-weight: bold; color: #483478; }
                    .route .arrow { color: #9b59b6; font-size: 22px; margin: 0 12px; }
                    .total { background: #483478; border-radius: 10px; padding: 16px 20px; display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
                    .total-label { color: #c8b8e8; font-size: 14px; }
                    .total-value { color: white; font-size: 24px; font-weight: bold; }
                    .booking-id { text-align: center; background: #ede5ff; border-radius: 8px; padding: 12px; margin-bottom: 20px; }
                    .booking-id span { font-size: 22px; font-weight: bold; color: #483478; letter-spacing: 2px; }
                    .footer { background: #f8f4ff; padding: 16px 32px; text-align: center; color: #aaa; font-size: 12px; }
                    .success-badge { color: #28a050; font-size: 13px; font-weight: bold; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🚆 RailMatrix</h1>
                        <p>Your booking is confirmed!</p>
                    </div>
                    <div class="body">
                        <p class="greeting">Hello <strong>%s</strong>,<br>Your ticket has been booked successfully. Here are your journey details:</p>

                        <div class="booking-id">
                            Booking ID &nbsp; <span>#%d</span>
                        </div>

                        <div class="route">
                            <span class="from">%s</span>
                            <span class="arrow">→</span>
                            <span class="to">%s</span>
                        </div>

                        <div class="ticket">
                            <div class="ticket-row">
                                <span class="ticket-label">Train</span>
                                <span class="ticket-value">%s</span>
                            </div>
                            <div class="ticket-row">
                                <span class="ticket-label">Journey Date</span>
                                <span class="ticket-value">%s</span>
                            </div>
                            <div class="ticket-row">
                                <span class="ticket-label">Passengers</span>
                                <span class="ticket-value">%d</span>
                            </div>
                            <div class="ticket-row">
                                <span class="ticket-label">Payment Method</span>
                                <span class="ticket-value success-badge">✓ %s</span>
                            </div>
                        </div>

                        <div class="total">
                            <span class="total-label">Total Paid</span>
                            <span class="total-value">₹%s</span>
                        </div>

                        <p style="color:#888; font-size:13px; text-align:center;">
                            Please carry a valid photo ID during your journey.<br>
                            For cancellations, visit the RailMatrix app.
                        </p>
                    </div>
                    <div class="footer">
                        © 2026 RailMatrix · This is an automated email, please do not reply.
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                userName, bookingId,
                departure, destination,
                trainName, journeyDate,
                passengers, paymentMethod,
                String.format("%.0f", totalFare)
        );
    }

    // ────────────────────────────────────────────────────────────
    // CANCELLATION EMAIL HTML TEMPLATE
    // ────────────────────────────────────────────────────────────
    private static String buildCancellationEmailHTML(String userName, int bookingId,
                                                     String trainName, String journeyDate,
                                                     double refundAmount) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <style>
                    body { font-family: 'Helvetica Neue', Arial, sans-serif; background: #f5f0ff; margin: 0; padding: 20px; }
                    .container { max-width: 560px; margin: 0 auto; background: white; border-radius: 16px; overflow: hidden; box-shadow: 0 4px 20px rgba(72,52,120,0.15); }
                    .header { background: linear-gradient(135deg, #8b0000, #c0392b); padding: 28px 32px; }
                    .header h1 { color: white; margin: 0; font-size: 22px; }
                    .header p { color: #f5b8b8; margin: 4px 0 0; font-size: 14px; }
                    .body { padding: 28px 32px; }
                    .greeting { font-size: 16px; color: #32235a; margin-bottom: 20px; }
                    .info { background: #fff5f5; border: 1px solid #f5c6c6; border-radius: 12px; padding: 20px; margin-bottom: 20px; }
                    .info-row { display: flex; justify-content: space-between; padding: 6px 0; border-bottom: 1px solid #ffe0e0; }
                    .info-row:last-child { border-bottom: none; }
                    .info-label { color: #888; font-size: 13px; }
                    .info-value { color: #32235a; font-size: 13px; font-weight: bold; }
                    .refund { background: #28a050; border-radius: 10px; padding: 16px 20px; display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
                    .refund-label { color: #c8f0d8; font-size: 14px; }
                    .refund-value { color: white; font-size: 24px; font-weight: bold; }
                    .footer { background: #f8f4ff; padding: 16px 32px; text-align: center; color: #aaa; font-size: 12px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>🚆 RailMatrix</h1>
                        <p>Your booking has been cancelled</p>
                    </div>
                    <div class="body">
                        <p class="greeting">Hello <strong>%s</strong>,<br>Your booking has been successfully cancelled. Here are the details:</p>

                        <div class="info">
                            <div class="info-row">
                                <span class="info-label">Booking ID</span>
                                <span class="info-value">#%d</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Train</span>
                                <span class="info-value">%s</span>
                            </div>
                            <div class="info-row">
                                <span class="info-label">Journey Date</span>
                                <span class="info-value">%s</span>
                            </div>
                        </div>

                        <div class="refund">
                            <span class="refund-label">Refund Amount</span>
                            <span class="refund-value">₹%s</span>
                        </div>

                        <p style="color:#888; font-size:13px; text-align:center;">
                            Refund will be credited to your original payment method within 5-7 business days.
                        </p>
                    </div>
                    <div class="footer">
                        © 2026 RailMatrix · This is an automated email, please do not reply.
                    </div>
                </div>
            </body>
            </html>
            """.formatted(
                userName, bookingId,
                trainName, journeyDate,
                String.format("%.0f", refundAmount)
        );
    }
}
