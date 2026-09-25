# Vehicle Rental Management System

A terminal-based Vehicle Rental Management System built using Core Java. The project is being developed incrementally and now includes a MySQL database connection through JDBC.

## Commit 6 - Vehicle CRUD with MySQL

This commit adds database-backed CRUD operations for vehicles.

### Added
- `VehicleDAO` for create, read, update, and delete operations
- `PreparedStatement` for all vehicle SQL operations
- try-with-resources for JDBC resources
- Mapping of database rows back to `Car` and `Motorcycle` objects
- Generated vehicle IDs from MySQL
- `VehicleCrudTest` console class for testing CRUD operations
- Commit 6 vehicle schema migration for existing Commit 5 databases

### MySQL Setup

Create the database user and database in MySQL:

```sql
CREATE DATABASE vehicle_rental_db;

CREATE USER 'rental_user'@'localhost' IDENTIFIED BY 'Rental@123';

GRANT ALL PRIVILEGES ON vehicle_rental_db.*
TO 'rental_user'@'localhost';

FLUSH PRIVILEGES;
```

Then run `schema.sql` for a fresh database.

If you already created the database using Commit 5, run:

```text
sql/commit6_vehicle_migration.sql
```

This adds the vehicle fields required by the Java `Car` and `Motorcycle` models.

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

Customer and rental CRUD, rental workflow, input validation, custom exception handling, and the final menu-driven console interface will be added in later commits.
