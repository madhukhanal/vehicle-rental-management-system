package model;

/**
 * Base class for all vehicles available for rental.
 */
public abstract class Vehicle {
    private int id;
    private String registrationNumber;
    private String brand;
    private String model;
    private double dailyRate;
    private boolean available;

    public Vehicle(int id, String registrationNumber, String brand,
                   String model, double dailyRate) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.model = model;
        this.dailyRate = dailyRate;
        this.available = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public abstract String getVehicleType();

    @Override
    public String toString() {
        return String.format("%d | %s | %s %s | NPR %.2f/day | %s",
                id, registrationNumber, brand, model, dailyRate,
                available ? "Available" : "Rented");
    }
}
