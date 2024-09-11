package com.example.cab302project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private TextField UsernameField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Label LoginStatus;

    private LoginSystem loginSystem = new LoginSystem();

    // handle login button
    @FXML
    public void HandleLoginAction() {
        String username = UsernameField.getText();
        String password = PasswordField.getText();

        // Check if the username and password are valid using login system
        if (loginSystem.checkLogin(username, password)) {
            LoginStatus.setText("Login successful!");
        } else {
            LoginStatus.setText("Login failed. Invalid username or password.");
        }
    }


    public LoginController() {
        SqliteConnection LoginDAO = new SqliteConnection();
    }

}