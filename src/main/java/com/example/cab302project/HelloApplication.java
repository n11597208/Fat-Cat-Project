package com.example.cab302project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load the FXML file for the Edit Profile page
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("EditProfile.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600); // Adjust dimensions as needed

        // Correctly reference the CSS file using an absolute path in the resource folder
        URL cssResource = HelloApplication.class.getResource("style.css");
        if (cssResource != null) {
            scene.getStylesheets().add(cssResource.toExternalForm());
        } else {
            System.err.println("CSS file not found! Check the file path.");
        }

        stage.setTitle("Edit Profile - Rate My Ride");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
