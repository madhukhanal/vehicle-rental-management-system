package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides PostgreSQL database connections using environment variables.
 *
 * Required environment variables:
 * DB_URL      (example: jdbc:postgresql://localhost:5432/vehicle_rental_db)
 * DB_USER     (example: postgres)
 * DB_PASSWORD (your PostgreSQL password)
 */
public final class DatabaseConnection {

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        String url = System.getenv("DB_URL");
        String user = System.getenv("DB_USER");
        String password = System.getenv("DB_PASSWORD");

        if (url == null || user == null || password == null) {
            throw new SQLException(
                    "Database configuration is missing. Set DB_URL, DB_USER, and DB_PASSWORD."
            );
        }

        return DriverManager.getConnection(url, user, password);
    }
}
