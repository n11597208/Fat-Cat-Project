package com.example.cab302project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.cab302project.LoginController.Session.getLoggedInUser;


public class CreatePostController {
    @FXML
    private Button cancelButton;
    @FXML
    private TextField PostTitleField;

    @FXML
    private TextField PostDescriptionField;

    @FXML
    private TextField CarMakeField;

    @FXML
    private TextField CarModelField;

    @FXML
    private TextField LocationField;

    @FXML
    private Button postButton;


    public void handleUploadFile(ActionEvent actionEvent) {
    }

    public void onCancelButtonClick(ActionEvent actionEvent) throws IOException {
        PostTitleField.clear();
        PostDescriptionField.clear();
        CarMakeField.clear();
        CarModelField.clear();
        LocationField.clear();
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);

    }

    public void onPostButtonClick(ActionEvent actionEvent) throws IOException {
        String postTitle = PostTitleField.getText();
        String postDescription = PostDescriptionField.getText();
        String carMake = CarMakeField.getText();
        String carModel = CarModelField.getText();
        String location = LocationField.getText();

        // Assuming the userId is fetched from the current session or context
        String userName = getLoggedInUser(); // Replace this with actual logic to get the logged-in user's ID

        Post newPost = new Post(postTitle, postDescription, "author", carModel, carMake, location);

        SQLitePostDOA postDAO = new SQLitePostDOA();
        postDAO.addPost(newPost, userName);
        Stage stage = (Stage) postButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Profile_UI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    private void onAdd() {
        // Default values for a new contact
        final String DEFAULT_TITLE = "";
        final String DEFAULT_DESCRIPTION = "";
        final String DEFAULT_MAKE = "";
        final String DEFAULT_MODEL = "";
        final String DEFAULT_LOCATION = "";
        Post newPost = new Post(DEFAULT_TITLE, DEFAULT_DESCRIPTION, DEFAULT_MAKE, DEFAULT_MODEL, DEFAULT_LOCATION, DEFAULT_TITLE);
        PostManager.addPost(newPost);
    }
}
