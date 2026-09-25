package main;

import model.Car;
import model.Customer;
import model.Motorcycle;
import model.Rental;
import model.Vehicle;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("Vehicle Rental Management System");
        System.out.println("--------------------------------");

        Vehicle car = new Car(1, "BA 1 PA 1234", "Toyota", "Corolla", 4500, 5);
        Vehicle motorcycle = new Motorcycle(2, "BA 99 PA 5678", "Honda", "CB Shine", 1800, 125);
        Customer customer = new Customer(
                1,
                "Demo Customer",
                "9800000000",
                "demo@example.com",
                "DL-001"
        );

        System.out.println("Vehicle: " + car);
        System.out.println("Vehicle: " + motorcycle);
        System.out.println("Customer: " + customer);

        Rental rental = new Rental(
                1,
                car,
                customer,
                LocalDate.now(),
                LocalDate.now().plusDays(3)
        );

        System.out.println("Rental: " + rental);
    }
}
