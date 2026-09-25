USE vehicle_rental_db;

-- Commit 5 created a simpler vehicles table.
-- Commit 6 expands it so the database can store the existing
-- Car and Motorcycle model fields used by the Java application.

ALTER TABLE vehicles
    ADD COLUMN registration_number VARCHAR(50) NULL,
    ADD COLUMN brand VARCHAR(100) NULL,
    ADD COLUMN vehicle_type VARCHAR(30) NULL,
    ADD COLUMN number_of_seats INT NULL,
    ADD COLUMN engine_capacity INT NULL;

UPDATE vehicles
SET brand = make
WHERE brand IS NULL;

UPDATE vehicles
SET registration_number = CONCAT('LEGACY-', id)
WHERE registration_number IS NULL;

UPDATE vehicles
SET vehicle_type = 'Car'
WHERE vehicle_type IS NULL;

ALTER TABLE vehicles
    MODIFY registration_number VARCHAR(50) NOT NULL,
    MODIFY brand VARCHAR(100) NOT NULL,
    MODIFY vehicle_type VARCHAR(30) NOT NULL;
