package main;

import model.Car;
import model.Customer;
import model.Motorcycle;
import model.Rental;
import model.Vehicle;
import service.VehicleService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Vehicle Rental Management System");
        System.out.println("--------------------------------");

        VehicleService vehicleService = new VehicleService();

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

        System.out.println("\nAll vehicles:");
        for (Vehicle vehicle : vehicleService.getAllVehicles()) {
            System.out.println(vehicle.getVehicleType() + " -> " + vehicle);
        }

        System.out.println("\nVehicle found by ID 2:");
        System.out.println(vehicleService.findById(2));

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
