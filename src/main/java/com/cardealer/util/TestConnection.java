package com.cardealer.util;

import com.cardealer.util.DBConnection;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()) {
            System.out.println("Connected to database successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}