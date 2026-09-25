package dao;

import model.Customer;
import model.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides CRUD operations for customers stored in MySQL.
 */
public class CustomerDAO {

    private static final String INSERT_SQL =
            "INSERT INTO customers (name, phone, email, driving_license_number) VALUES (?, ?, ?, ?)";
    private static final String SELECT_BY_ID_SQL =
            "SELECT id, name, phone, email, driving_license_number FROM customers WHERE id = ?";
    private static final String SELECT_ALL_SQL =
            "SELECT id, name, phone, email, driving_license_number FROM customers ORDER BY id";
    private static final String UPDATE_SQL =
            "UPDATE customers SET name = ?, phone = ?, email = ?, driving_license_number = ? WHERE id = ?";
    private static final String DELETE_SQL =
            "DELETE FROM customers WHERE id = ?";

    public int create(Customer customer) throws SQLException {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getDrivingLicenseNumber());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Creating customer failed: no row was inserted.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    customer.setId(id);
                    return id;
                }
            }
        }

        throw new SQLException("Creating customer failed: no generated ID was returned.");
    }

    public Customer findById(int id) throws SQLException {
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

    public List<Customer> findAll() throws SQLException {
        List<Customer> customers = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_ALL_SQL);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                customers.add(mapRow(resultSet));
            }
        }

        return customers;
    }

    public boolean update(Customer customer) throws SQLException {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null.");
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, customer.getName());
            statement.setString(2, customer.getPhone());
            statement.setString(3, customer.getEmail());
            statement.setString(4, customer.getDrivingLicenseNumber());
            statement.setInt(5, customer.getId());
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

    private Customer mapRow(ResultSet resultSet) throws SQLException {
        return new Customer(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("phone"),
                resultSet.getString("email"),
                resultSet.getString("driving_license_number")
        );
    }
}
