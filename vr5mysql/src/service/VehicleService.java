package service;

import model.Vehicle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages vehicles using Java Collections Framework.
 * ArrayList preserves the display order while HashMap provides fast ID lookup.
 */
public class VehicleService {
    private final List<Vehicle> vehicles = new ArrayList<>();
    private final Map<Integer, Vehicle> vehicleById = new HashMap<>();

    public void addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }
        if (vehicleById.containsKey(vehicle.getId())) {
            throw new IllegalArgumentException("Vehicle ID already exists: " + vehicle.getId());
        }

        vehicles.add(vehicle);
        vehicleById.put(vehicle.getId(), vehicle);
    }

    public Vehicle findById(int id) {
        return vehicleById.get(id);
    }

    public boolean removeById(int id) {
        Vehicle vehicle = vehicleById.remove(id);
        if (vehicle == null) {
            return false;
        }
        return vehicles.remove(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }

    public List<Vehicle> getAvailableVehicles() {
        List<Vehicle> availableVehicles = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.isAvailable()) {
                availableVehicles.add(vehicle);
            }
        }
        return availableVehicles;
    }

    public List<Vehicle> getVehiclesSortedByDailyRate() {
        List<Vehicle> sortedVehicles = new ArrayList<>(vehicles);
        sortedVehicles.sort(Comparator.comparingDouble(Vehicle::getDailyRate));
        return sortedVehicles;
    }

    public int size() {
        return vehicles.size();
    }
}
