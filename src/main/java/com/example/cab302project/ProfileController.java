package com.example.cab302project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileController {

    @FXML
    private HBox hbox;  // Make sure this matches your FXML fx:id for the HBox

    @FXML
    private Label welcomeText1;

    @FXML
    private Label welcomeText2;

    @FXML
    private Label welcomeText3;

    @FXML
    private Region spacer;

    @FXML
    private Region spacer2;

    @FXML
    private Button nextButton;

    private LoginController.Session session;  // Declare session at the class level

    @FXML
    public void initialize() {
        // Initialize session and update labels
        session = new LoginController.Session();  // Initialize session
        changeLabelText();  // Call the method to update the labels

        // Set the Hgrow priority for the spacers to make them expand
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
    }

    @FXML
    protected void onNextButtonClick() throws IOException {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Create_Post.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    private void changeLabelText() {
        // Get the current logged-in user and set the label text
        String currentUser = session.getLoggedInUser();
        welcomeText1.setText("Welcome, " + currentUser);  // Update label with the user's name
    }
}
