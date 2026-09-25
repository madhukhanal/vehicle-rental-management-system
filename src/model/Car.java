package model;

public class Car extends Vehicle {
    private int numberOfSeats;

    public Car(int id, String registrationNumber, String brand,
               String model, double dailyRate, int numberOfSeats) {
        super(id, registrationNumber, brand, model, dailyRate);
        this.numberOfSeats = numberOfSeats;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }
}
