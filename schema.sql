-- Vehicle Rental Management System - MySQL schema

CREATE DATABASE IF NOT EXISTS vehicle_rental_db;
USE vehicle_rental_db;

CREATE TABLE IF NOT EXISTS vehicles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    registration_number VARCHAR(50) NOT NULL,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    daily_rate DECIMAL(10, 2) NOT NULL,
    available BOOLEAN NOT NULL DEFAULT TRUE,
    vehicle_type VARCHAR(30) NOT NULL,
    number_of_seats INT NULL,
    engine_capacity INT NULL
);

CREATE TABLE IF NOT EXISTS customers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    email VARCHAR(150),
    driving_license_number VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS rentals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    vehicle_id INT NOT NULL,
    customer_id INT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_cost DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_rental_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(id),
    CONSTRAINT fk_rental_customer FOREIGN KEY (customer_id) REFERENCES customers(id),
    CONSTRAINT chk_rental_dates CHECK (end_date >= start_date),
    CONSTRAINT chk_rental_cost CHECK (total_cost >= 0)
);
