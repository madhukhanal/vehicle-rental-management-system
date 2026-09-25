package main;

import dao.CustomerDAO;
import dao.RentalDAO;
import dao.VehicleDAO;
import model.Car;
import model.Customer;
import model.Rental;
import model.Vehicle;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Small console test for Commit 7 customer and rental CRUD operations.
 */
public class CustomerRentalCrudTest {
    public static void main(String[] args) {
        CustomerDAO customerDAO = new CustomerDAO();
        VehicleDAO vehicleDAO = new VehicleDAO();
        RentalDAO rentalDAO = new RentalDAO();

        try {
            Customer customer = new Customer(
                    0,
                    "Commit 7 Customer",
                    "9800000000",
                    "commit7@example.com",
                    "DL-C7-001"
            );
            int customerId = customerDAO.create(customer);
            System.out.println("Created customer with ID: " + customerId);

            customer.setPhone("9811111111");
            System.out.println("Updated customer: " + customerDAO.update(customer));
            System.out.println("Found customer: " + customerDAO.findById(customerId));

            Vehicle vehicle = new Car(
                    0,
                    "C7-RENT-001",
                    "Toyota",
                    "Yaris",
                    4000.00,
                    5
            );
            int vehicleId = vehicleDAO.create(vehicle);
            System.out.println("Created vehicle with ID: " + vehicleId);

            Rental rental = new Rental(
                    0,
                    vehicle,
                    customer,
                    LocalDate.now(),
                    LocalDate.now().plusDays(2)
            );
            int rentalId = rentalDAO.create(rental);
            System.out.println("Created rental with ID: " + rentalId);
            System.out.println("Found rental: " + rentalDAO.findById(rentalId));

            List<Rental> rentals = rentalDAO.findAll();
            System.out.println("All rentals:");
            for (Rental storedRental : rentals) {
                System.out.println(storedRental);
            }

            rental.setReturnDate(LocalDate.now().plusDays(3));
            System.out.println("Updated rental: " + rentalDAO.update(rental));

            System.out.println("Deleted rental: " + rentalDAO.delete(rentalId));
            System.out.println("Deleted customer: " + customerDAO.delete(customerId));
            System.out.println("Deleted vehicle: " + vehicleDAO.delete(vehicleId));

        } catch (SQLException | IllegalArgumentException e) {
            System.out.println("Customer/Rental CRUD operation failed: " + e.getMessage());
        }
    }
}
