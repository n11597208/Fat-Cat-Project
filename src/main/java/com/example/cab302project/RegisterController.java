package com.example.cab302project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class RegisterController {

    @FXML
    private ImageView logoImageView;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField lastnameTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField setpasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label confirmPasswordLabel;

    @FXML
    private Label registrationMessageLabel;

    @FXML
    private Button closeButton;

    @FXML
    private Label statusLabel;

    @FXML

    private RegistrationSystem registrationSystem = new RegistrationSystem();

    public void registerButtonOnAction(ActionEvent event) throws IOException {
        if (validateInputs()) {
            if (setpasswordField.getText().equals(confirmPasswordField.getText())) {
                registrationMessageLabel.setText("Registration successful!");
                confirmPasswordLabel.setText("Passwords match.");
                registerUser();
            } else {
                confirmPasswordLabel.setText("Passwords does not match.");
            }
        }
        Stage stage = (Stage) closeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login_UI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    public void closeButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login_UI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    private boolean validateInputs() {
        if (firstnameTextField.getText().isEmpty() || lastnameTextField.getText().isEmpty() ||
                emailTextField.getText().isEmpty() || usernameTextField.getText().isEmpty() ||
                setpasswordField.getText().isEmpty() || confirmPasswordField.getText().isEmpty()) {
            registrationMessageLabel.setText("Please fill in all fields.");
            return false;
        }
        return true;
    }

    private void registerUser() {
        String firstName = firstnameTextField.getText();
        String lastName = lastnameTextField.getText();
        String email = emailTextField.getText();
        String username = usernameTextField.getText();
        String password = setpasswordField.getText();
        registrationSystem.addUser(firstName, lastName, email, username, password);
//        statusLabel.setText("User registered successfully.");
    }
}
