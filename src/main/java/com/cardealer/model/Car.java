package com.cardealer.model;

public class Car {

    private int id; // 🔥 IMPORTANT (from database)
    private String brand;
    private String model;
    private double price;
    private int quantity;

    // Constructor WITHOUT id (used when inserting new car)
    public Car(String brand, String model, double price, int quantity) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }

    // Constructor WITH id (used when reading from DB)
    public Car(int id, String brand, String model, double price, int quantity) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    // Optional: reduce stock by quantity
    public void reduceStock(int qty) {
        if (quantity >= qty) {
            quantity -= qty;
        }
    }

    @Override
    public String toString() {
        return "ID: " + id +
                ", Brand: " + brand +
                ", Model: " + model +
                ", Price: $" + price +
                ", Stock: " + quantity;
    }
}