USE vehicle_rental_db;

-- Commit 7 adds the driving-license field required by the Customer model.
ALTER TABLE customers
    ADD COLUMN driving_license_number VARCHAR(50) NULL;
