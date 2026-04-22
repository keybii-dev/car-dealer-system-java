package com.cardealer.model;

public class Transaction {

    private int id;
    private int buyerId;
    private int carId;
    private int quantityBought;
    private double totalPrice;

    public Transaction(int buyerId, int carId, int quantityBought, double totalPrice) {
        this.buyerId = buyerId;
        this.carId = carId;
        this.quantityBought = quantityBought;
        this.totalPrice = totalPrice;
    }

    public Transaction(int id, int buyerId, int carId, int quantityBought, double totalPrice) {
        this.id = id;
        this.buyerId = buyerId;
        this.carId = carId;
        this.quantityBought = quantityBought;
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public int getBuyerId() {
        return buyerId;
    }

    public int getCarId() {
        return carId;
    }

    public int getQuantityBought() {
        return quantityBought;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + id +
                ", Buyer ID: " + buyerId +
                ", Car ID: " + carId +
                ", Quantity Bought: " + quantityBought +
                ", Total Price: $" + totalPrice;
    }
}