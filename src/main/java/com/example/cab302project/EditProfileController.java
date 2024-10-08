package com.example.cab302project;

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

    @FXML
    private void handleSave() {
        // Add logic to save the profile information
        System.out.println("Profile saved successfully!");
    }

    @FXML
    private void handleCancel() {
        // Add logic to handle cancel action
        System.out.println("Changes canceled.");
    }
}


