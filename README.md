# Vehicle Rental Management System

A terminal-based Vehicle Rental Management System built using Core Java. The project is being developed incrementally and will later use JDBC for persistent storage of vehicles, customers, and rentals.

## Commit 4 - Customer Management

This commit adds an in-memory customer management service using the Java Collections Framework.

### Added
- `CustomerService` service class
- `ArrayList<Customer>` for maintaining customer records
- `HashMap<Integer, Customer>` for fast customer ID lookup
- Add customer operation with duplicate-ID validation
- Find customer by ID
- Remove customer by ID
- List all customers
- Search customers by name
- Sort customers alphabetically by name
- Updated `Main` to demonstrate customer management

### Current Project Structure

```text
src/
├── main/
│   └── Main.java
├── model/
│   ├── Car.java
│   ├── Customer.java
│   ├── Motorcycle.java
│   ├── Rental.java
│   └── Vehicle.java
└── service/
    ├── CustomerService.java
    └── VehicleService.java
```

## Planned Later Work

The database connection, JDBC CRUD operations, rental workflow, input validation, exception handling, and final menu-driven console interface will be added in later commits.
