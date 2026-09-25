package service;

import dao.CustomerDAO;
import dao.RentalDAO;
import dao.VehicleDAO;
import exception.CustomerNotFoundException;
import exception.RentalValidationException;
import exception.VehicleNotFoundException;
import exception.VehicleUnavailableException;
import model.Customer;
import model.Rental;
import model.Vehicle;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * Handles the business workflow for creating and completing rentals.
 */
public class RentalService {
    private final RentalDAO rentalDAO;
    private final VehicleDAO vehicleDAO;
    private final CustomerDAO customerDAO;

    public RentalService() {
        this.rentalDAO = new RentalDAO();
        this.vehicleDAO = new VehicleDAO();
        this.customerDAO = new CustomerDAO();
    }

    public Rental createRental(int vehicleId, int customerId,
                               LocalDate rentalDate, LocalDate returnDate)
            throws SQLException, VehicleNotFoundException,
            CustomerNotFoundException, VehicleUnavailableException,
            RentalValidationException {

        validateDates(rentalDate, returnDate);

        Vehicle vehicle = vehicleDAO.findById(vehicleId);
        if (vehicle == null) {
            throw new VehicleNotFoundException("Vehicle not found: " + vehicleId);
        }
        if (!vehicle.isAvailable()) {
            throw new VehicleUnavailableException(
                    "Vehicle " + vehicleId + " is currently unavailable.");
        }

        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found: " + customerId);
        }

        Rental rental = new Rental(0, vehicle, customer, rentalDate, returnDate);
        rentalDAO.create(rental);

        vehicle.setAvailable(false);
        vehicleDAO.update(vehicle);

        return rental;
    }

    public boolean completeRental(int rentalId)
            throws SQLException, RentalValidationException {
        Rental rental = rentalDAO.findById(rentalId);
        if (rental == null) {
            throw new RentalValidationException("Rental not found: " + rentalId);
        }

        boolean deleted = rentalDAO.delete(rentalId);
        if (deleted) {
            Vehicle vehicle = rental.getVehicle();
            vehicle.setAvailable(true);
            vehicleDAO.update(vehicle);
        }
        return deleted;
    }

    public Rental findRental(int rentalId) throws SQLException {
        return rentalDAO.findById(rentalId);
    }

    public List<Rental> getAllRentals() throws SQLException {
        return rentalDAO.findAll();
    }

    private void validateDates(LocalDate rentalDate, LocalDate returnDate)
            throws RentalValidationException {
        if (rentalDate == null || returnDate == null) {
            throw new RentalValidationException("Rental dates cannot be empty.");
        }
        if (rentalDate.isBefore(LocalDate.now())) {
            throw new RentalValidationException("Rental date cannot be in the past.");
        }
        if (returnDate.isBefore(rentalDate)) {
            throw new RentalValidationException("Return date cannot be before rental date.");
        }
        if (returnDate.equals(rentalDate)) {
            throw new RentalValidationException("Rental must be at least one day.");
        }
    }
}
