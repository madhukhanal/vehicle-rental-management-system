package main;

import model.Car;
import model.Customer;
import model.Motorcycle;
import model.Rental;
import model.Vehicle;
import service.CustomerService;
import service.VehicleService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Vehicle Rental Management System");
        System.out.println("--------------------------------");

        VehicleService vehicleService = new VehicleService();
        CustomerService customerService = new CustomerService();

        Vehicle car = new Car(1, "BA 1 PA 1234", "Toyota", "Corolla", 4500, 5);
        Vehicle motorcycle = new Motorcycle(2, "BA 99 PA 5678", "Honda", "CB Shine", 1800, 125);

        vehicleService.addVehicle(car);
        vehicleService.addVehicle(motorcycle);

        Customer customer = new Customer(
                1,
                "Demo Customer",
                "9800000000",
                "demo@example.com",
                "DL-001"
        );
        Customer secondCustomer = new Customer(
                2,
                "Ram Sharma",
                "9811111111",
                "ram@example.com",
                "DL-002"
        );

        customerService.addCustomer(customer);
        customerService.addCustomer(secondCustomer);

        System.out.println("\nAll vehicles:");
        for (Vehicle vehicle : vehicleService.getAllVehicles()) {
            System.out.println(vehicle.getVehicleType() + " -> " + vehicle);
        }

        System.out.println("\nAll customers:");
        for (Customer storedCustomer : customerService.getAllCustomers()) {
            System.out.println(storedCustomer);
        }

        System.out.println("\nCustomer found by ID 2:");
        System.out.println(customerService.findById(2));

        System.out.println("\nCustomers matching 'ram':");
        for (Customer matchingCustomer : customerService.searchByName("ram")) {
            System.out.println(matchingCustomer);
        }

        System.out.println("\nCustomers sorted by name:");
        for (Customer sortedCustomer : customerService.getCustomersSortedByName()) {
            System.out.println(sortedCustomer);
        }

        System.out.println("\nVehicles sorted by daily rate:");
        for (Vehicle vehicle : vehicleService.getVehiclesSortedByDailyRate()) {
            System.out.printf("%s -> NPR %.2f/day%n",
                    vehicle.getVehicleType(), vehicle.getDailyRate());
        }

        System.out.println("\nAvailable vehicles: " + vehicleService.getAvailableVehicles().size());

        Rental rental = new Rental(
                1,
                car,
                customer,
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );

        car.setAvailable(false);
        System.out.println("\nRental: " + rental);
        System.out.println("Available vehicles after rental: "
                + vehicleService.getAvailableVehicles().size());
    }
}
