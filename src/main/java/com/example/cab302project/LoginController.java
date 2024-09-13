package com.example.cab302project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField UsernameField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Label LoginStatus;

    private LoginSystem loginSystem = new LoginSystem();

    // Handle login button
    @FXML
    public void HandleLoginAction() {
        String username = UsernameField.getText();
        String password = PasswordField.getText();

        // Check if the username and password are valid using login system
        if (loginSystem.checkLogin(username, password)) {
            LoginStatus.setText("Login successful!");

            try {
                // Get the current stage
                Stage window = (Stage) UsernameField.getScene().getWindow();

                // Load the new FXML file (Profile_UI.fxml)
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
                Parent root = fxmlLoader.load(); // Load the Profile UI

                // Create a new scene with the specified width and height
                Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);

                // Set the new scene on the window (stage)
                window.setScene(scene);

                // Show the window with the new scene
                window.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            LoginStatus.setText("Login failed. Invalid username or password.");
        }
    }

    public LoginController() {
        SqliteConnection LoginDAO = new SqliteConnection();
    }
}
