package dao;

import model.Customer;
import model.DatabaseConnection;
import model.Rental;
import model.Vehicle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides CRUD operations for rentals stored in MySQL.
 */
public class RentalDAO {

    private static final String INSERT_SQL =
            "INSERT INTO rentals (vehicle_id, customer_id, start_date, end_date, total_cost) " +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT id, vehicle_id, customer_id, start_date, end_date, total_cost " +
            "FROM rentals WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT id, vehicle_id, customer_id, start_date, end_date, total_cost " +
            "FROM rentals ORDER BY id";
    private static final String UPDATE_SQL =
            "UPDATE rentals SET vehicle_id = ?, customer_id = ?, start_date = ?, " +
            "end_date = ?, total_cost = ? WHERE id = ?";
    private static final String DELETE_SQL =
            "DELETE FROM rentals WHERE id = ?";

    private final VehicleDAO vehicleDAO;
    private final CustomerDAO customerDAO;

    public RentalDAO() {
        this.vehicleDAO = new VehicleDAO();
        this.customerDAO = new CustomerDAO();
    }

    public int create(Rental rental) throws SQLException {
        validateRental(rental);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, rental.getVehicle().getId());
            statement.setInt(2, rental.getCustomer().getId());
            statement.setDate(3, Date.valueOf(rental.getRentalDate()));
            statement.setDate(4, Date.valueOf(rental.getReturnDate()));
            statement.setDouble(5, rental.calculateTotalCost());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Creating rental failed: no row was inserted.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    rental.setId(id);
                    return id;
                }
            }
        }

        throw new SQLException("Creating rental failed: no generated ID was returned.");
    }

    public Rental findById(int id) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_BY_ID_SQL)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }
        }
        return null;
    }

    public List<Rental> findAll() throws SQLException {
        List<Rental> rentals = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                rentals.add(mapRow(resultSet));
            }
        }

        return rentals;
    }

    public boolean update(Rental rental) throws SQLException {
        validateRental(rental);

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setInt(1, rental.getVehicle().getId());
            statement.setInt(2, rental.getCustomer().getId());
            statement.setDate(3, Date.valueOf(rental.getRentalDate()));
            statement.setDate(4, Date.valueOf(rental.getReturnDate()));
            statement.setDouble(5, rental.calculateTotalCost());
            statement.setInt(6, rental.getId());
            return statement.executeUpdate() > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private Rental mapRow(ResultSet resultSet) throws SQLException {
        int vehicleId = resultSet.getInt("vehicle_id");
        int customerId = resultSet.getInt("customer_id");

        Vehicle vehicle = vehicleDAO.findById(vehicleId);
        Customer customer = customerDAO.findById(customerId);

        if (vehicle == null) {
            throw new SQLException("Rental references missing vehicle ID: " + vehicleId);
        }
        if (customer == null) {
            throw new SQLException("Rental references missing customer ID: " + customerId);
        }

        LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
        LocalDate endDate = resultSet.getDate("end_date").toLocalDate();

        return new Rental(
                resultSet.getInt("id"),
                vehicle,
                customer,
                startDate,
                endDate
        );
    }

    private void validateRental(Rental rental) {
        if (rental == null) {
            throw new IllegalArgumentException("Rental cannot be null.");
        }
        if (rental.getVehicle() == null || rental.getCustomer() == null) {
            throw new IllegalArgumentException("Rental must have a vehicle and customer.");
        }
        if (rental.getRentalDate() == null || rental.getReturnDate() == null) {
            throw new IllegalArgumentException("Rental dates cannot be null.");
        }
        if (rental.getReturnDate().isBefore(rental.getRentalDate())) {
            throw new IllegalArgumentException("Return date cannot be before rental date.");
        }
    }
}
