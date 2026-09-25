package main;

import dao.VehicleDAO;
import model.Car;
import model.Vehicle;

import java.sql.SQLException;
import java.util.List;

/**
 * Small console test for the Commit 6 vehicle CRUD operations.
 */
public class VehicleCrudTest {
    public static void main(String[] args) {
        VehicleDAO vehicleDAO = new VehicleDAO();

        try {
            Vehicle vehicle = new Car(
                    0,
                    "BA-01-PA-0001",
                    "Toyota",
                    "Corolla",
                    4500.00,
                    5
            );

            int id = vehicleDAO.create(vehicle);
            System.out.println("Created vehicle with ID: " + id);

            Vehicle found = vehicleDAO.findById(id);
            System.out.println("Found vehicle: " + found);

            vehicle.setDailyRate(5000.00);
            vehicle.setAvailable(false);
            System.out.println("Updated: " + vehicleDAO.update(vehicle));

            System.out.println("All vehicles:");
            List<Vehicle> vehicles = vehicleDAO.findAll();
            for (Vehicle storedVehicle : vehicles) {
                System.out.println(storedVehicle.getVehicleType() + " -> " + storedVehicle);
            }

            System.out.println("Deleted: " + vehicleDAO.delete(id));

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Vehicle CRUD operation failed: " + e.getMessage());
        }
    }
}
