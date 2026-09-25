package main;

import dao.CustomerDAO;
import dao.RentalDAO;
import dao.VehicleDAO;
import exception.CustomerNotFoundException;
import exception.RentalValidationException;
import exception.VehicleNotFoundException;
import exception.VehicleUnavailableException;
import model.Car;
import model.Customer;
import model.Motorcycle;
import model.Rental;
import model.Vehicle;
import service.RentalService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Final menu-driven console application for the Vehicle Rental Management System.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final VehicleDAO vehicleDAO = new VehicleDAO();
    private static final CustomerDAO customerDAO = new CustomerDAO();
    private static final RentalDAO rentalDAO = new RentalDAO();
    private static final RentalService rentalService = new RentalService();

    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("      VEHICLE RENTAL MANAGEMENT SYSTEM");
        System.out.println("========================================");

        boolean running = true;

        while (running) {
            printMainMenu();
            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        vehicleMenu();
                        break;
                    case 2:
                        customerMenu();
                        break;
                    case 3:
                        rentalMenu();
                        break;
                    case 4:
                        running = false;
                        System.out.println("\nThank you for using the Vehicle Rental Management System.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please select 1-4.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            } catch (RuntimeException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n========================================");
        System.out.println("              MAIN MENU");
        System.out.println("========================================");
        System.out.println("1. Vehicle Management");
        System.out.println("2. Customer Management");
        System.out.println("3. Rental Management");
        System.out.println("4. Exit");
        System.out.println("========================================");
    }

    private static void vehicleMenu() throws SQLException {
        boolean back = false;

        while (!back) {
            System.out.println("\n----------- VEHICLE MANAGEMENT -----------");
            System.out.println("1. Add Vehicle");
            System.out.println("2. View All Vehicles");
            System.out.println("3. Find Vehicle");
            System.out.println("4. Update Vehicle");
            System.out.println("5. Delete Vehicle");
            System.out.println("6. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addVehicle();
                    break;
                case 2:
                    viewVehicles();
                    break;
                case 3:
                    findVehicle();
                    break;
                case 4:
                    updateVehicle();
                    break;
                case 5:
                    deleteVehicle();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-6.");
            }
        }
    }

    private static void addVehicle() throws SQLException {
        System.out.println("\nAdd Vehicle");
        String type = readNonEmpty("Vehicle type (Car/Motorcycle): ");

        String registration = readNonEmpty("Registration number: ");
        String brand = readNonEmpty("Brand: ");
        String model = readNonEmpty("Model: ");
        double dailyRate = readPositiveDouble("Daily rental rate (NPR): ");

        Vehicle vehicle;

        if (type.equalsIgnoreCase("Car")) {
            int seats = readPositiveInt("Number of seats: ");
            vehicle = new Car(0, registration, brand, model, dailyRate, seats);
        } else if (type.equalsIgnoreCase("Motorcycle")) {
            int engineCapacity = readPositiveInt("Engine capacity (cc): ");
            vehicle = new Motorcycle(0, registration, brand, model, dailyRate, engineCapacity);
        } else {
            System.out.println("Invalid vehicle type.");
            return;
        }

        int id = vehicleDAO.create(vehicle);
        System.out.println("Vehicle added successfully. ID: " + id);
    }

    private static void viewVehicles() throws SQLException {
        List<Vehicle> vehicles = vehicleDAO.findAll();

        System.out.println("\n----------- ALL VEHICLES -----------");

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
            return;
        }

        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle + " | Type: " + vehicle.getVehicleType());
        }
    }

    private static void findVehicle() throws SQLException {
        int id = readPositiveInt("Enter vehicle ID: ");
        Vehicle vehicle = vehicleDAO.findById(id);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
        } else {
            System.out.println("Vehicle: " + vehicle);
            System.out.println("Type: " + vehicle.getVehicleType());
        }
    }

    private static void updateVehicle() throws SQLException {
        int id = readPositiveInt("Enter vehicle ID to update: ");
        Vehicle existing = vehicleDAO.findById(id);

        if (existing == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.println("Current vehicle: " + existing);

        String registration = readNonEmpty("Registration number: ");
        String brand = readNonEmpty("Brand: ");
        String model = readNonEmpty("Model: ");
        double dailyRate = readPositiveDouble("Daily rental rate (NPR): ");

        if (existing instanceof Car) {
            int seats = readPositiveInt("Number of seats: ");
            existing = new Car(id, registration, brand, model, dailyRate, seats);
        } else {
            int engineCapacity = readPositiveInt("Engine capacity (cc): ");
            existing = new Motorcycle(id, registration, brand, model, dailyRate, engineCapacity);
        }

        existing.setAvailable(vehicleDAO.findById(id).isAvailable());

        if (vehicleDAO.update(existing)) {
            System.out.println("Vehicle updated successfully.");
        } else {
            System.out.println("Vehicle was not updated.");
        }
    }

    private static void deleteVehicle() throws SQLException {
        int id = readPositiveInt("Enter vehicle ID to delete: ");
        Vehicle vehicle = vehicleDAO.findById(id);

        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        if (!vehicle.isAvailable()) {
            System.out.println("Cannot delete a vehicle that is currently rented.");
            return;
        }

        if (vehicleDAO.delete(id)) {
            System.out.println("Vehicle deleted successfully.");
        } else {
            System.out.println("Vehicle was not deleted.");
        }
    }

    private static void customerMenu() throws SQLException {
        boolean back = false;

        while (!back) {
            System.out.println("\n----------- CUSTOMER MANAGEMENT -----------");
            System.out.println("1. Add Customer");
            System.out.println("2. View All Customers");
            System.out.println("3. Find Customer");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6. Back");

            int choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    viewCustomers();
                    break;
                case 3:
                    findCustomer();
                    break;
                case 4:
                    updateCustomer();
                    break;
                case 5:
                    deleteCustomer();
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1-6.");
            }
        }
    }

    private static void addCustomer() throws SQLException {
        System.out.println("\nAdd Customer");

        String name = readNonEmpty("Name: ");
        String phone = readNonEmpty("Phone: ");
        String email = readOptional("Email: ");
        String license = readNonEmpty("Driving license number: ");

        Customer customer = new Customer(0, name, phone, email, license);
        int id = customerDAO.create(customer);

        System.out.println("Customer added successfully. ID: " + id);
    }

    private static void viewCustomers() throws SQLException {
        List<Customer> customers = customerDAO.findAll();

        System.out.println("\n----------- ALL CUSTOMERS -----------");

        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }

        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    private static void findCustomer() throws SQLException {
        int id = readPositiveInt("Enter customer ID: ");
        Customer customer = customerDAO.findById(id);

        if (customer == null) {
            System.out.println("Customer not found.");
        } else {
            System.out.println("Customer: " + customer);
        }
    }

    private static void updateCustomer() throws SQLException {
        int id = readPositiveInt("Enter customer ID to update: ");
        Customer existing = customerDAO.findById(id);

        if (existing == null) {
            System.out.println("Customer not found.");
            return;
        }

        String name = readNonEmpty("Name: ");
        String phone = readNonEmpty("Phone: ");
        String email = readOptional("Email: ");
        String license = readNonEmpty("Driving license number: ");

        Customer updated = new Customer(id, name, phone, email, license);

        if (customerDAO.update(updated)) {
            System.out.println("Customer updated successfully.");
        } else {
            System.out.println("Customer was not updated.");
        }
    }

    private static void deleteCustomer() throws SQLException {
        int id = readPositiveInt("Enter customer ID to delete: ");

        if (customerDAO.delete(id)) {
            System.out.println("Customer deleted successfully.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void rentalMenu() throws SQLException {
        boolean back = false;

        while (!back) {
            System.out.println("\n------------ RENTAL MANAGEMENT ------------");
            System.out.println("1. Start Rental");
            System.out.println("2. Complete Rental");
            System.out.println("3. View Active Rentals");
            System.out.println("4. Find Rental");
            System.out.println("5. Back");

            int choice = readInt("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        startRental();
                        break;
                    case 2:
                        completeRental();
                        break;
                    case 3:
                        viewRentals();
                        break;
                    case 4:
                        findRental();
                        break;
                    case 5:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please select 1-5.");
                }
            } catch (VehicleNotFoundException | CustomerNotFoundException |
                     VehicleUnavailableException | RentalValidationException e) {
                System.out.println("Rental error: " + e.getMessage());
            }
        }
    }

    private static void startRental()
            throws SQLException, VehicleNotFoundException,
            CustomerNotFoundException, VehicleUnavailableException,
            RentalValidationException {

        System.out.println("\nStart Rental");

        int vehicleId = readPositiveInt("Vehicle ID: ");
        int customerId = readPositiveInt("Customer ID: ");
        LocalDate startDate = readDate("Rental date (YYYY-MM-DD): ");
        LocalDate endDate = readDate("Return date (YYYY-MM-DD): ");

        Rental rental = rentalService.createRental(
                vehicleId, customerId, startDate, endDate);

        System.out.println("Rental created successfully.");
        System.out.println(rental);
    }

    private static void completeRental()
            throws SQLException, RentalValidationException {

        int rentalId = readPositiveInt("Rental ID to complete: ");

        Rental rental = rentalService.findRental(rentalId);

        if (rental == null) {
            System.out.println("Rental not found.");
            return;
        }

        if (rentalService.completeRental(rentalId)) {
            System.out.println("Rental completed successfully.");
            System.out.println("Vehicle " + rental.getVehicle().getRegistrationNumber()
                    + " is now available.");
        } else {
            System.out.println("Rental could not be completed.");
        }
    }

    private static void viewRentals() throws SQLException {
        List<Rental> rentals = rentalService.getAllRentals();

        System.out.println("\n----------- ACTIVE RENTALS -----------");

        if (rentals.isEmpty()) {
            System.out.println("No active rentals found.");
            return;
        }

        for (Rental rental : rentals) {
            System.out.println(rental);
        }
    }

    private static void findRental() throws SQLException {
        int id = readPositiveInt("Enter rental ID: ");
        Rental rental = rentalService.findRental(id);

        if (rental == null) {
            System.out.println("Rental not found.");
        } else {
            System.out.println("Rental: " + rental);
        }
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Value must be greater than zero.");
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                double value = Double.parseDouble(input);
                if (value > 0) {
                    return value;
                }
            } catch (NumberFormatException ignored) {
                // Ask again below.
            }

            System.out.println("Please enter a positive number.");
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }

    private static String readOptional(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            String input = readNonEmpty(prompt);

            try {
                return LocalDate.parse(input);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Use YYYY-MM-DD.");
            }
        }
    }
}
