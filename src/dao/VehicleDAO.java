package dao;

import model.Car;
import model.Motorcycle;
import model.Vehicle;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.DatabaseConnection;

/**
 * Provides CRUD operations for vehicles stored in MySQL.
 */
public class VehicleDAO {

    private static final String INSERT_SQL =
            "INSERT INTO vehicles " +
            "(registration_number, brand, model, daily_rate, available, vehicle_type, number_of_seats, engine_capacity) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_BY_ID_SQL =
            "SELECT id, registration_number, brand, model, daily_rate, available, " +
            "vehicle_type, number_of_seats, engine_capacity " +
            "FROM vehicles WHERE id = ?";

    private static final String SELECT_ALL_SQL =
            "SELECT id, registration_number, brand, model, daily_rate, available, " +
            "vehicle_type, number_of_seats, engine_capacity " +
            "FROM vehicles ORDER BY id";

    private static final String UPDATE_SQL =
            "UPDATE vehicles SET registration_number = ?, brand = ?, model = ?, " +
            "daily_rate = ?, available = ?, vehicle_type = ?, number_of_seats = ?, engine_capacity = ? " +
            "WHERE id = ?";

    private static final String DELETE_SQL =
            "DELETE FROM vehicles WHERE id = ?";

    /**
     * Inserts a vehicle and assigns the generated database ID to the object.
     */
    public int create(Vehicle vehicle) throws SQLException {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            setVehicleParameters(statement, vehicle);

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating vehicle failed: no row was inserted.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    vehicle.setId(generatedId);
                    return generatedId;
                }
            }

            throw new SQLException("Creating vehicle failed: no generated ID was returned.");
        }
    }

    /**
     * Finds one vehicle by its database ID.
     */
    public Vehicle findById(int id) throws SQLException {
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

    /**
     * Returns all vehicles stored in the database.
     */
    public List<Vehicle> findAll() throws SQLException {
        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                vehicles.add(mapRow(resultSet));
            }
        }

        return vehicles;
    }

    /**
     * Updates an existing vehicle by ID.
     */
    public boolean update(Vehicle vehicle) throws SQLException {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null.");
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            setVehicleParameters(statement, vehicle);
            statement.setInt(9, vehicle.getId());

            return statement.executeUpdate() > 0;
        }
    }

    /**
     * Deletes a vehicle by ID.
     */
    public boolean delete(int id) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {

            statement.setInt(1, id);
            return statement.executeUpdate() > 0;
        }
    }

    private void setVehicleParameters(PreparedStatement statement, Vehicle vehicle)
            throws SQLException {
        statement.setString(1, vehicle.getRegistrationNumber());
        statement.setString(2, vehicle.getBrand());
        statement.setString(3, vehicle.getModel());
        statement.setDouble(4, vehicle.getDailyRate());
        statement.setBoolean(5, vehicle.isAvailable());
        statement.setString(6, vehicle.getVehicleType());

        if (vehicle instanceof Car) {
            statement.setInt(7, ((Car) vehicle).getNumberOfSeats());
            statement.setNull(8, java.sql.Types.INTEGER);
        } else if (vehicle instanceof Motorcycle) {
            statement.setNull(7, java.sql.Types.INTEGER);
            statement.setInt(8, ((Motorcycle) vehicle).getEngineCapacity());
        } else {
            statement.setNull(7, java.sql.Types.INTEGER);
            statement.setNull(8, java.sql.Types.INTEGER);
        }
    }

    private Vehicle mapRow(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String registrationNumber = resultSet.getString("registration_number");
        String brand = resultSet.getString("brand");
        String model = resultSet.getString("model");
        double dailyRate = resultSet.getDouble("daily_rate");
        boolean available = resultSet.getBoolean("available");
        String vehicleType = resultSet.getString("vehicle_type");

        Vehicle vehicle;
        if ("Motorcycle".equalsIgnoreCase(vehicleType)) {
            int engineCapacity = resultSet.getInt("engine_capacity");
            vehicle = new Motorcycle(id, registrationNumber, brand, model, dailyRate, engineCapacity);
        } else {
            int numberOfSeats = resultSet.getInt("number_of_seats");
            vehicle = new Car(id, registrationNumber, brand, model, dailyRate, numberOfSeats);
        }

        vehicle.setAvailable(available);
        return vehicle;
    }
}
