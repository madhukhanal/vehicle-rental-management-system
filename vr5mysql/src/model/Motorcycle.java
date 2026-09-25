package model;

public class Motorcycle extends Vehicle {
    private int engineCapacity;

    public Motorcycle(int id, String registrationNumber, String brand,
                      String model, double dailyRate, int engineCapacity) {
        super(id, registrationNumber, brand, model, dailyRate);
        this.engineCapacity = engineCapacity;
    }

    public int getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(int engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    @Override
    public String getVehicleType() {
        return "Motorcycle";
    }
}
