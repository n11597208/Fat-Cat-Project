package com.example.cab302project.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RegistrationSystem {

    public void addUser(String firstName, String lastName, String email, String userName, String password, byte[] profilePicture, String description) {
        String sql = "INSERT INTO users (firstName, lastName, email, userName, password, profilePicture, followers, numberOfPosts, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        if (firstName == "" || lastName == "" || email == "" || userName == "" || password == "" || duplicateUser(email)) {
            return;
        } else {
            try (Connection conn = SqliteConnection.connect();
                 PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, firstName);
                statement.setString(2, lastName);
                statement.setString(3, email);
                statement.setString(4, userName);
                statement.setString(5, password);
                statement.setBytes(6, profilePicture);
                statement.setInt(7, 0);
                statement.setInt(8, 0);
                statement.setString(9, description);
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    public boolean duplicateUser(String email){
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = SqliteConnection.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}

