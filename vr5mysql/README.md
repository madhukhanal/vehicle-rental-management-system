# Vehicle Rental Management System

A terminal-based Vehicle Rental Management System built using Core Java. The project is being developed incrementally and now includes a MySQL database connection through JDBC.

## Commit 5 - MySQL JDBC Connection and Database Schema

This commit adds the database foundation for persistent storage.

### Added
- MySQL Connector/J dependency through Maven
- `DatabaseConnection` class for JDBC connections
- Environment-variable based database configuration
- MySQL database schema for vehicles, customers, and rentals
- `DatabaseTest` class to verify the JDBC connection
- `.env.example` showing the required database variables
- `.gitignore` protection for local environment configuration

### MySQL Setup

Create the database user and database in MySQL:

```sql
CREATE DATABASE vehicle_rental_db;

CREATE USER 'rental_user'@'localhost' IDENTIFIED BY 'Rental@123';

GRANT ALL PRIVILEGES ON vehicle_rental_db.*
TO 'rental_user'@'localhost';

FLUSH PRIVILEGES;
```

Then run `schema.sql` to create the tables.

### Environment Variables

Set these values in your local environment:

```text
DB_URL=jdbc:mysql://localhost:3306/vehicle_rental_db
DB_USER=rental_user
DB_PASSWORD=Rental@123
```

Do not commit a real `.env` file or real credentials to GitHub.

## Current Project Structure

```text
src/
├── main/
│   └── Main.java
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
    └── VehicleService.java
```

## Planned Later Work

The database CRUD operations, rental workflow, input validation, custom exception handling, and final menu-driven console interface will be added in later commits.
