package com.example.cab302project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationSystem {

    public void addUser(String firstName, String lastName, String email, String userName, String password) {
        String sql = "INSERT INTO users (firstName, lastName, email, userName, password, followers, numberOfPosts) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = SqliteConnection.connect();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, userName);
            statement.setString(5, password);
            statement.setInt(6, 0);
            statement.setInt(7, 0);
            statement.executeUpdate();


//            ResultSet generatedKeys = statement.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                post.setId(generatedKeys.getInt(1));
//            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}

