package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Rental {
    private int id;
    private Vehicle vehicle;
    private Customer customer;
    private LocalDate rentalDate;
    private LocalDate returnDate;

    public Rental(int id, Vehicle vehicle, Customer customer,
                  LocalDate rentalDate, LocalDate returnDate) {
        this.id = id;
        this.vehicle = vehicle;
        this.customer = customer;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public long getRentalDays() {
        return ChronoUnit.DAYS.between(rentalDate, returnDate);
    }

    public double calculateTotalCost() {
        return getRentalDays() * vehicle.getDailyRate();
    }

    @Override
    public String toString() {
        return String.format("Rental #%d | %s | Customer: %s | %s to %s | Total: NPR %.2f",
                id, vehicle.getRegistrationNumber(), customer.getName(),
                rentalDate, returnDate, calculateTotalCost());
    }
}
