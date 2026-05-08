package com.smartrail.dao;

import com.smartrail.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDAO {
    public static boolean adminLogin(String email, String password) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM admins WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
