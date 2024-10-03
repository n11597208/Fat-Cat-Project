package com.example.cab302project.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public User getUser(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?"; // Use the correct column name
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Integer userID = resultSet.getInt("id");
                    String firstName = resultSet.getString("firstName");
                    String lastName = resultSet.getString("lastName");
                    String email = resultSet.getString("email");
                    String userName = resultSet.getString("userName");
                    Integer followers = resultSet.getInt("followers");
                    Integer numberOfPosts = resultSet.getInt("numberOfPosts");
                    String description = resultSet.getString("description");


                    User user = new User(userID, firstName, lastName, email, userName, followers, numberOfPosts, description);
                    return  user;
                    }
                } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }




        return null; // Returns null if not found
    }
    public ObservableList<String> getAllUsers(String username) {
    String sql = "SELECT username FROM users WHERE username != ?";
    ObservableList userList = FXCollections.observableArrayList();
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, username);
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                userList.add(resultSet.getString("username"));
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    return userList;
    }
}

