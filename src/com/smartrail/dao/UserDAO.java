package com.smartrail.dao;

import com.smartrail.model.User;
import com.smartrail.util.DBConnection;

import java.security.MessageDigest;
import java.sql.*;
import java.util.UUID;

public class UserDAO {

    // ── REGISTER USER (basic — no verification, for backwards compat) ──
    public boolean register(User user) {
        String query = "INSERT INTO users(name, email, password, is_verified) VALUES (?, ?, ?, 1)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, hashPassword(user.getPassword()));

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ── REGISTER WITH EMAIL VERIFICATION ──────────────────────────────
    // Returns the verification token (to be emailed), or null on failure
    public String registerWithVerification(User user) {
        String token = UUID.randomUUID().toString().replace("-", "");
        String query = "INSERT INTO users(name, email, password, is_verified, verification_token) VALUES (?, ?, ?, 0, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, hashPassword(user.getPassword()));
            ps.setString(4, token);

            int rows = ps.executeUpdate();
            return rows > 0 ? token : null;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ── VERIFY EMAIL TOKEN ────────────────────────────────────────────
    public boolean verifyEmailToken(String token) {
        String query = "UPDATE users SET is_verified = 1, verification_token = NULL WHERE verification_token = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, token);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ── LOGIN USER ────────────────────────────────────────────────────
    // Returns the User if credentials match AND email is verified.
    // Returns null with a reason code via the LoginResult wrapper.
    public LoginResult loginWithStatus(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return new LoginResult(null, LoginStatus.NOT_FOUND);
            }

            // Check password
            if (!rs.getString("password").equals(hashPassword(password))) {
                return new LoginResult(null, LoginStatus.WRONG_PASSWORD);
            }

            // Check email verified
            if (rs.getInt("is_verified") == 0) {
                return new LoginResult(null, LoginStatus.NOT_VERIFIED);
            }

            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            return new LoginResult(user, LoginStatus.SUCCESS);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new LoginResult(null, LoginStatus.ERROR);
    }

    // ── LEGACY LOGIN (kept for compatibility) ─────────────────────────
    public User login(String email, String password) {
        LoginResult result = loginWithStatus(email, password);
        return result.user;
    }

    // ── CHECK USER EXISTS ─────────────────────────────────────────────
    public boolean isUserExists(String email) {
        String query = "SELECT user_id FROM users WHERE email = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ── SHA-256 HASH — must match Java RegisterGUI and Node.js API ────
    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return password;
        }
    }

    // ── Login result types ────────────────────────────────────────────
    public enum LoginStatus { SUCCESS, NOT_FOUND, WRONG_PASSWORD, NOT_VERIFIED, ERROR }

    public static class LoginResult {
        public final User user;
        public final LoginStatus status;
        public LoginResult(User user, LoginStatus status) {
            this.user   = user;
            this.status = status;
        }
    }
}
