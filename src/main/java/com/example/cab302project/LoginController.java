package com.example.cab302project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

    //handle login button
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

    @FXML
    public void HandleRegisterAction(ActionEvent actionEvent) throws IOException {
        // load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent newRoot = loader.load();

        // get the current stage from the event
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        // set the new scene
        Scene scene = new Scene(newRoot);
        stage.setScene(scene);
        stage.show();

    }


    public LoginController() {
        SqliteConnection LoginDAO = new SqliteConnection();
    }

}