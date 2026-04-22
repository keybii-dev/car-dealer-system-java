package com.cardealer;

import com.cardealer.dao.CarDao;
import com.cardealer.dao.UserDao;
import com.cardealer.model.Car;
import com.cardealer.model.User;
import com.cardealer.dao.TransactionDao;
import com.cardealer.model.Transaction;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {
            User user = login(scanner);

            if (user == null) {
                System.out.println("Invalid login.");
            } else if (user.getRole().equalsIgnoreCase("SELLER")) {
                sellerMenu(scanner);
            } else if (user.getRole().equalsIgnoreCase("BUYER")) {
                buyerMenu(scanner, user);
            }

            System.out.print("Do you want to exit the program? (yes/no): ");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("yes")) {
                running = false;
            }
        }

        scanner.close();
        System.out.println("Program ended.");
    }

    private static User login(Scanner scanner) {
        System.out.println("\n=== LOGIN ===");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        UserDao userDao = new UserDao();
        User user = userDao.login(username, password);

        return user; // 🔥 return full user
    }


    //SELLER SCREEN ----------------------------------------------------------------------------------------------------
    private static void sellerMenu(Scanner scanner) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n=== SELLER MENU ===");
            System.out.println("1. Add Car");
            System.out.println("2. View Cars");
            System.out.println("3. Update Car");
            System.out.println("4. Delete Car");
            System.out.println("5. Logout");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addCar(scanner);
                    break;
                case 2:
                    viewCars();
                    break;
                case 3:
                    updateCar(scanner);
                    break;
                case 4:
                    deleteCar(scanner);
                    break;
                case 5:
                    loggedIn = false;
                    System.out.println("Seller logged out.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private static void addCar(Scanner scanner) {
        System.out.println("\n=== ADD CAR ===");

        System.out.print("Enter brand: ");
        String brand = scanner.nextLine();

        System.out.print("Enter model: ");
        String model = scanner.nextLine();

        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Car car = new Car(brand, model, price, quantity);

        CarDao carDao = new CarDao();
        carDao.addCar(car);

        System.out.println("Car added successfully.");
    }

    private static void viewCars() {
        System.out.println("\n=== CAR LIST ===");

        CarDao carDao = new CarDao();
        List<Car> cars = carDao.getAllCars();

        if (cars.isEmpty()) {
            System.out.println("No cars available.");
            return;
        }

        for (Car car : cars) {
            System.out.println(car);
        }
    }

    private static void updateCar(Scanner scanner) {

        CarDao carDao = new CarDao();

        viewCars();

        System.out.print("Enter car ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter new brand: ");
        String brand = scanner.nextLine();

        System.out.print("Enter new model: ");
        String model = scanner.nextLine();

        System.out.print("Enter new price: ");
        double price = scanner.nextDouble();

        System.out.print("Enter new quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Car updatedCar = new Car(id, brand, model, price, quantity);

        carDao.updateCar(updatedCar);
    }

    private static void deleteCar(Scanner scanner) {

        CarDao carDao = new CarDao();

        viewCars();

        System.out.print("Enter car ID to delete: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        carDao.deleteCar(id);
    }


    // BUYER SCREEN ---------------------------------------------------------------------------------------------------

    private static void buyerMenu(Scanner scanner, User user) {
        boolean loggedIn = true;

        while (loggedIn) {
            System.out.println("\n=== BUYER MENU ===");
            System.out.println("1. View Cars");
            System.out.println("2. Buy Car");
            System.out.println("3. View My Purchases");
            System.out.println("4. Logout");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    viewCars();
                    break;
                case 2:
                    buyCar(scanner, user);
                    break;
                case 3:
                    viewMyPurchases(user);
                    break;
                case 4:
                    loggedIn = false;
                    System.out.println("Buyer logged out.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }


    private static void buyCar(Scanner scanner, User user) {

        CarDao carDao = new CarDao();
        TransactionDao transactionDao = new TransactionDao();

        viewCars();

        System.out.print("Enter car ID to buy: ");
        int carId = scanner.nextInt();

        System.out.print("Enter quantity to buy: ");
        int qty = scanner.nextInt();
        scanner.nextLine();

        Car car = carDao.getCarById(carId);

        if (car == null) {
            System.out.println("Car not found.");
            return;
        }

        if (qty <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }

        if (car.getQuantity() < qty) {
            System.out.println("Not enough stock available.");
            return;
        }

        double totalPrice = car.getPrice() * qty;

        int newQuantity = car.getQuantity() - qty;
        Car updatedCar = new Car(
                car.getId(),
                car.getBrand(),
                car.getModel(),
                car.getPrice(),
                newQuantity
        );

        carDao.updateCar(updatedCar);

        Transaction transaction = new Transaction(user.getId(), carId, qty, totalPrice);
        transactionDao.addTransaction(transaction);

        System.out.println("Purchase successful!");
        System.out.println("Total price: $" + totalPrice);
    }

    private static void viewMyPurchases(User user) {
        System.out.println("\n=== MY PURCHASES ===");


        TransactionDao transactionDao = new TransactionDao();
        CarDao carDao = new CarDao();
        List<Transaction> transactions = transactionDao.getTransactionsByBuyerId(user.getId());

        if (transactions.isEmpty()) {
            System.out.println("No purchases found.");
            return;
        }

        for (Transaction transaction : transactions) {

            Car car = carDao.getCarById(transaction.getCarId());

            String carInfo = "Unknown Car!";

            if (car != null){
                carInfo = car.getBrand() + " " + car.getModel();
            }

            System.out.println(
                    "Car: " + carInfo +
                    " | Quantity: " + transaction.getQuantityBought() +
                    " | Price: " + transaction.getTotalPrice());
        }
    }
}
