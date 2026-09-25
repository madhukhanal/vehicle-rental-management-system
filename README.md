# Vehicle Rental Management System

A terminal-based Vehicle Rental Management System built with Core Java, OOP, Java Collections, JDBC, MySQL, exception handling, and a menu-driven console interface.

## Features

### Vehicle Management
- Add cars and motorcycles
- View all vehicles
- Find a vehicle by ID
- Update vehicle information
- Delete available vehicles
- Track vehicle availability

### Customer Management
- Add customers
- View all customers
- Find a customer by ID
- Update customer information
- Delete customers

### Rental Management
- Start a rental
- Validate rental dates
- Check vehicle availability
- Check that the vehicle and customer exist
- Calculate rental cost
- Complete a rental and return the vehicle to available status
- View and find active rentals

## Technologies

- Java 11+
- MySQL
- JDBC
- Maven
- IntelliJ IDEA

## Project Structure

```text
src/
├── dao/
│   ├── CustomerDAO.java
│   ├── RentalDAO.java
│   └── VehicleDAO.java
├── exception/
│   ├── CustomerNotFoundException.java
│   ├── RentalValidationException.java
│   ├── VehicleNotFoundException.java
│   └── VehicleUnavailableException.java
├── main/
│   ├── CustomerRentalCrudTest.java
│   ├── Main.java
│   ├── RentalWorkflowTest.java
│   └── VehicleCrudTest.java
├── model/
│   ├── Car.java
│   ├── Customer.java
│   ├── DatabaseConnection.java
│   ├── DatabaseTest.java
│   ├── Motorcycle.java
│   ├── Rental.java
│   └── Vehicle.java
└── service/
    ├── CustomerService.java
    ├── RentalService.java
    └── VehicleService.java
```

## MySQL Setup

For a fresh database, run `schema.sql`.

For an existing database created in earlier commits, run the migration scripts in order as needed:

```text
sql/commit6_vehicle_migration.sql
sql/commit7_customer_rental_migration.sql
```

The final `vehicles` table requires:

- registration_number
- brand
- model
- daily_rate
- available
- vehicle_type
- number_of_seats
- engine_capacity

The final `customers` table requires `driving_license_number`.

## Environment Variables

Set these environment variables in the IntelliJ Run Configuration:

```text
DB_URL=jdbc:mysql://localhost:3306/vehicle_rental_db
DB_USER=your_mysql_username
DB_PASSWORD=your_mysql_password
```

Do not commit a real `.env` file or database password.

## Running the Application

1. Make sure MySQL Server is running.
2. Make sure the database and tables exist.
3. Reload the Maven project in IntelliJ.
4. Configure `DB_URL`, `DB_USER`, and `DB_PASSWORD`.
5. Run:

```text
main.Main
```

The application opens with:

```text
========================================
      VEHICLE RENTAL MANAGEMENT SYSTEM
========================================

1. Vehicle Management
2. Customer Management
3. Rental Management
4. Exit
```

Each section contains its own submenu so the main menu stays short and easy to navigate.

## Testing

The project includes separate console test classes:

- `DatabaseTest`
- `VehicleCrudTest`
- `CustomerRentalCrudTest`
- `RentalWorkflowTest`

`RentalWorkflowTest` verifies the rental workflow, including vehicle availability changes and validation.

## Git Commit History

The project was developed incrementally:

1. Initial project structure
2. Core OOP domain models
3. Collections-based vehicle management
4. Customer management
5. MySQL database connection and schema
6. Vehicle CRUD with MySQL
7. Customer and Rental CRUD with MySQL
8. Rental workflow and validation
9. Final menu, integration, testing, and documentation
