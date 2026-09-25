-- Vehicle Rental Management System - PostgreSQL schema

CREATE TABLE IF NOT EXISTS vehicles (
    id SERIAL PRIMARY KEY,
    make VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    daily_rate NUMERIC(10, 2) NOT NULL CHECK (daily_rate >= 0),
    available BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    email VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS rentals (
    id SERIAL PRIMARY KEY,
    vehicle_id INTEGER NOT NULL REFERENCES vehicles(id),
    customer_id INTEGER NOT NULL REFERENCES customers(id),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_cost NUMERIC(10, 2) NOT NULL CHECK (total_cost >= 0),
    CHECK (end_date >= start_date)
);
