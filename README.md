# Car Dealer System (Packaged Version)

This version uses Java packages so the project looks more organized and closer to a real project.

## Package guide

- `com.cardealer.model`
  - Contains the data objects of the system
  - Example: `Car`, `Customer`, `Transaction`

- `com.cardealer.service`
  - Contains the business logic
  - Example: adding cars, storing customers, buying cars

- `com.cardealer`
  - Contains the `Main` class
  - This is the entry point of the application

## Study order

1. `model/Car.java`
2. `model/Customer.java`
3. `model/Transaction.java`
4. `service/CarService.java`
5. `service/CustomerService.java`
6. `service/TransactionService.java`
7. `Main.java`

## Why no DAO yet?

I did not add `DAO` yet because you are still learning the basics.
DAO is usually used when saving data to a database or file.

Right now, this project stores data only in memory using `ArrayList`,
so `service` is enough for now.

Later, when you learn database + Spring Boot, we can add:
- `repository` or `dao`
- `controller`
- `entity`
