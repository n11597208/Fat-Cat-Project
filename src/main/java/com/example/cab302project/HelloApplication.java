package com.example.cab302project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static final int WIDTH = 640;
    public static final int HEIGHT = 700;

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