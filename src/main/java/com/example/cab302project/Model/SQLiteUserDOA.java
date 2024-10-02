package com.example.cab302project.Model;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteUserDOA {
    private static Connection connection;


    public SQLiteUserDOA() {
        connection = SqliteConnection.connect();
    }
    public Image getProfilePicture(String username) {
        String sql = "SELECT profilePicture FROM users WHERE username = ?"; // Use the correct column name
        Image image = null; // Local variable

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    InputStream inputStream = resultSet.getBinaryStream("profilePicture");
                    if (inputStream != null) {
                        // Convert InputStream to Image
                        image = new Image(inputStream);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return image; // Returns null if not found
    }
}

