package com.example.cab302project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteConnection {
    private static final String DB_URL = "jdbc:sqlite:database.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return conn;
    }



    // method to set up the database
    public static void setupDatabase() {
        // SQLite to create a users table if it doesn't exist
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT NOT NULL UNIQUE,
                password TEXT NOT NULL
            );
        """;

        // SQLite to insert a sample user
        String insertSampleUserSQL = """
            INSERT OR IGNORE INTO users (username, password) VALUES ('admin', '1234');
        """;

        try (Connection conn = connect(); Statement stmt = conn.createStatement()) {
            // Execute the table creation
            stmt.execute(createTableSQL);

            // Insert a sample user
            stmt.execute(insertSampleUserSQL);
            System.out.println("Database setup completed and sample user added.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
