package com.example.cab302project.Controller;

import com.example.cab302project.Model.User;
import com.example.cab302project.Model.UserProfileDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class EditProfileController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    private UserProfileDAO userProfileDAO = new UserProfileDAO();

    @FXML
    public void initialize() {
        String currentUsername = "john_doe";  // You should dynamically fetch the logged-in user's username
        loadUserProfile(currentUsername);
    }

    private void loadUserProfile(String username) {
        User userProfile = userProfileDAO.getUserProfile(username);
        if (userProfile != null) {
            firstNameField.setText(userProfile.getFirstName());
            lastNameField.setText(userProfile.getLastName());
            usernameField.setText(userProfile.getUserName());
            emailField.setText(userProfile.getEmail());
        }
    }

    @FXML
    public void saveProfile() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();

        userProfileDAO.updateUserProfile(firstName, lastName, username, email);
        // Add success message or alert to inform user the profile is updated
    }

    @FXML
    private void handleCancel() {
        // Add logic to handle cancel action
        System.out.println("Changes canceled.");
    }
}


