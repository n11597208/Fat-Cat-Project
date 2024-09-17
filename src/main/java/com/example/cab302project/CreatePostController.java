package com.example.cab302project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.example.cab302project.LoginController.Session.getLoggedInUser;

public class CreatePostController {
    // Declare global variables here
    private String filename = null; // Global variable for filename
    private int s = 0; // Global variable for s
    private byte[] post_image = null;

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

    @FXML
    private Button uploadButton;

    private Image selectedImage;

    @FXML
    public void initialize() {
        uploadButton.setOnAction(this::handleUploadFile);
    }

    @FXML
    public void handleUploadFile(ActionEvent actionEvent) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        if (file != null) {
            filename = file.getAbsolutePath(); // Now filename is global and accessible anywhere in the class
            try {
                FileInputStream fis = new FileInputStream(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buf = new byte[1024];
                int readNum;
                while ((readNum = fis.read(buf)) != -1) {
                    bos.write(buf, 0, readNum);
                }
                post_image = bos.toByteArray();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
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
        byte[] image = post_image;

        // Assuming the userId is fetched from the current session or context
        String userName = getLoggedInUser(); // Replace this with actual logic to get the logged-in user's ID

        Post newPost = new Post(postTitle, postDescription, "author", carModel, carMake, location, image, 0, 0, 0);

        SQLitePostDOA postDAO = new SQLitePostDOA();
        postDAO.addPost(newPost, userName);
        Stage stage = (Stage) postButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Profile_UI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    private void onAdd() {
        // Default values for a new post
        final String DEFAULT_TITLE = "";
        final String DEFAULT_DESCRIPTION = "";
        final String DEFAULT_MAKE = "";
        final String DEFAULT_MODEL = "";
        final String DEFAULT_LOCATION = "";
        final byte[] DEFAULT_IMAGE = null;
        final Integer DEFAULT_RATING = 0;
        final Integer DEFAULT_NUMBERCOMMENTS = 0;
        final Integer DEFAULT_NUMBERSHARES = 0;

        Post newPost = new Post(DEFAULT_TITLE, DEFAULT_DESCRIPTION, DEFAULT_MAKE, DEFAULT_MODEL, DEFAULT_LOCATION, DEFAULT_TITLE, DEFAULT_IMAGE, DEFAULT_RATING, DEFAULT_NUMBERCOMMENTS, DEFAULT_NUMBERSHARES);
        PostManager.addPost(newPost);
    }
}
