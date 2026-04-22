package com.cardealer.dao;

import com.cardealer.model.Transaction;
import com.cardealer.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    public void addTransaction(Transaction transaction) {

        String sql = "INSERT INTO transactions (buyer_id, car_id, quantity_bought, total_price) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, transaction.getBuyerId());
            stmt.setInt(2, transaction.getCarId());
            stmt.setInt(3, transaction.getQuantityBought());
            stmt.setDouble(4, transaction.getTotalPrice());

            stmt.executeUpdate();

            System.out.println("Transaction saved successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getTransactionsByBuyerId(int buyerId) {

        List<Transaction> transactions = new ArrayList<>();

        String sql = "SELECT * FROM transactions WHERE buyer_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, buyerId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int carId = rs.getInt("car_id");
                int quantityBought = rs.getInt("quantity_bought");
                double totalPrice = rs.getDouble("total_price");

                Transaction transaction = new Transaction(id, buyerId, carId, quantityBought, totalPrice);
                transactions.add(transaction);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }
}