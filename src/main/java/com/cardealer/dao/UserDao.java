package com.cardealer.dao;

import com.cardealer.model.User;
import com.cardealer.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDao {

    // LOGIN METHOD
    public User login(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // set values into query
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            // if match found
            if (rs.next()) {
                int id = rs.getInt("id");
                String user = rs.getString("username");
                String pass = rs.getString("password");
                String role = rs.getString("role");

                return new User(id, user, pass, role);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // no user found
        return null;
    }
}