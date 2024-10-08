package com.example.cab302project.Model.;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfileDAO {
    private Connection connection;

    public UserProfileDAO() {
        connection = SQLiteConnection.getInstance();
    }

    // Method to update user profile in the 'users' table
    public void updateUserProfile(String firstName, String lastName, String username, String email) {
        String updateQuery = "UPDATE users SET firstName = ?, lastName = ?, email = ? WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setString(1, firstName);
            pstmt.setString(2, lastName);
            pstmt.setString(3, email);
            pstmt.setString(4, username);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch user profile from the 'users' table
    public UserProfile getUserProfile(String username) {
        UserProfile userProfile = null;
        String selectQuery = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(selectQuery)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                userProfile = new UserProfile(firstName, lastName, username, email);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userProfile;
    }
}

