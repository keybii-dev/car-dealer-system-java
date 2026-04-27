package com.cardealer.dao;

import com.cardealer.model.Car;
import com.cardealer.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CarDao {

    // INSERT CAR INTO DATABASE
    public void addCar(Car car) {

        String sql = "INSERT INTO cars (brand, model, price, quantity) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setDouble(3, car.getPrice());
            stmt.setInt(4, car.getQuantity());

            stmt.executeUpdate();

            System.out.println("Car saved to database!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // GET ALL CARS FROM DATABASE
    public List<Car> getAllCars() {

        List<Car> cars = new ArrayList<>();

        String sql = "SELECT * FROM cars";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                int id = rs.getInt("id");
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                Car car = new Car(id, brand, model, price, quantity);
                cars.add(car);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return cars;

    }

    public Car getCarById(int id) {

        String sql = "SELECT * FROM cars WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String brand = rs.getString("brand");
                String model = rs.getString("model");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");

                return new Car(id, brand, model, price, quantity);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateCar(Car car) {

        String sql = "UPDATE cars SET brand = ?, model = ?, price = ?, quantity = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, car.getBrand());
            stmt.setString(2, car.getModel());
            stmt.setDouble(3, car.getPrice());
            stmt.setInt(4, car.getQuantity());
            stmt.setInt(5, car.getId());


            int rowsUpdated = stmt.executeUpdate();


            if (rowsUpdated > 0) {
                System.out.println("Car updated successfully!");
            } else {
                System.out.println("Car not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCar(int id) {

        String sql = "DELETE FROM cars WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Car deleted successfully!");
            } else {
                System.out.println("Car not found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}