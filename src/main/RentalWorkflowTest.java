package main;

import exception.CustomerNotFoundException;
import exception.RentalValidationException;
import exception.VehicleNotFoundException;
import exception.VehicleUnavailableException;
import model.Car;
import model.Customer;
import model.Vehicle;
import model.Rental;
import dao.CustomerDAO;
import dao.VehicleDAO;
import service.RentalService;

import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Console test for Commit 8 rental validation and workflow.
 */
public class RentalWorkflowTest {
    public static void main(String[] args) {
        VehicleDAO vehicleDAO = new VehicleDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        RentalService rentalService = new RentalService();

        Vehicle vehicle = new Car(0, "C8-WORK-001", "Toyota", "Vitz", 3500.00, 5);
        Customer customer = new Customer(
                0, "Commit 8 Customer", "9822222222",
                "commit8@example.com", "DL-C8-001");

        try {
            vehicleDAO.create(vehicle);
            customerDAO.create(customer);

            System.out.println("Created vehicle: " + vehicle.getId());
            System.out.println("Created customer: " + customer.getId());

            Rental rental = rentalService.createRental(
                    vehicle.getId(),
                    customer.getId(),
                    LocalDate.now(),
                    LocalDate.now().plusDays(3));

            System.out.println("Created rental: " + rental);
            System.out.println("Vehicle available after rental: " +
                    vehicleDAO.findById(vehicle.getId()).isAvailable());

            try {
                rentalService.createRental(
                        vehicle.getId(), customer.getId(),
                        LocalDate.now(), LocalDate.now().plusDays(2));
            } catch (VehicleUnavailableException e) {
                System.out.println("Validation worked: " + e.getMessage());
            }

            System.out.println("Completed rental: " +
                    rentalService.completeRental(rental.getId()));
            System.out.println("Vehicle available after completion: " +
                    vehicleDAO.findById(vehicle.getId()).isAvailable());

            customerDAO.delete(customer.getId());
            vehicleDAO.delete(vehicle.getId());
            System.out.println("Test data cleaned up.");

        } catch (SQLException | VehicleNotFoundException |
                 CustomerNotFoundException | VehicleUnavailableException |
                 RentalValidationException e) {
            System.out.println("Rental workflow failed: " + e.getMessage());
        }
    }
}
