package com.example.cab302project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // Set up the database and insert a sample user
        SqliteConnection.setupDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_UI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("RateMyRide!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}