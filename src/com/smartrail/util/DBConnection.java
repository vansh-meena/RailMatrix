package com.smartrail.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // ── Switch between LOCAL and CLOUD ───────────────────────────
    private static final boolean USE_CLOUD = true; // set false for local dev

    // ── Local DB ─────────────────────────────────────────────────
    private static final String LOCAL_URL  = "jdbc:mysql://localhost:3306/railmatrix";
    private static final String LOCAL_USER = "root";
    private static final String LOCAL_PASS = "12345678";

    // ── Cloud DB (Railway) ────────────────────────────────────────
    private static final String CLOUD_URL  = "jdbc:mysql://yamabiko.proxy.rlwy.net:46681/railway?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String CLOUD_USER = "root";
    private static final String CLOUD_PASS = "UeGOKYwJMppCSQtJRJxWcufzKHQZMQOx";

    public static Connection getConnection() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        if (USE_CLOUD) {
            return DriverManager.getConnection(CLOUD_URL, CLOUD_USER, CLOUD_PASS);
        } else {
            return DriverManager.getConnection(LOCAL_URL, LOCAL_USER, LOCAL_PASS);
        }
    }
}