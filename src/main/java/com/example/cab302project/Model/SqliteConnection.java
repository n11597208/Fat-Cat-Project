package com.example.cab302project.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteConnection {
    // Database URL
    private static final String DB_URL = "jdbc:sqlite:database.db";

    // Singleton instance of the connection
    private static Connection connection = null;

    // Private constructor to prevent instantiation
    private SqliteConnection() {
    }

    /**
     * Returns the singleton instance of the SQLite connection.
     * If it doesn't exist, a new connection is created.
     *
     * @return the singleton database connection
     */
    public static Connection connect() {
        try {
            if (connection == null || connection.isClosed()) {  // Check if the connection is null or closed
                connection = DriverManager.getConnection(DB_URL);
                System.out.println("Connection established.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
        return connection;
    }

    /**
     * Sets up the database by creating the users table if it does not exist,
     * and optionally inserts a sample user.
     */
    public static void setupDatabase() {
        // SQL to create a users table
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    firstName TEXT NOT NULL,
                    lastName TEXT NOT NULL,
                    email TEXT NOT NULL,        
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL,                                            

                    followers INTEGER NOT NULL,
                    numberOfPosts INTEGER NOT NULL,
                    profilePicture BLOB NULL,
                    description TEXT NULL
                );
            """;

        // SQL to insert a sample user
        String insertSampleUserSQL = """
                INSERT OR IGNORE INTO users (username, password, followers, numberOfPosts, firstName, lastName, email, description) 
                VALUES ('admin', '1234', 0, 0, 'John', 'Doe', 'John.Doe@gmail.com', 'This is my new RateMyRide Page');
            """;

        try (Statement stmt = connect().createStatement()) {  // Use singleton connection
            // Execute table creation
            stmt.execute(createTableSQL);

            // Insert a sample user
            stmt.execute(insertSampleUserSQL);
            System.out.println("Database setup completed and sample user added.");

        } catch (SQLException e) {
            System.out.println("Error setting up database: " + e.getMessage());
        }
    }

    /**
     * Closes the database connection.
     */
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
