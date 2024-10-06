package com.example.cab302project.Controller;

import com.example.cab302project.HelloApplication;
import com.example.cab302project.Model.Post;
import com.example.cab302project.Model.SQLitePostDOA;
import com.example.cab302project.Model.SQLiteUserDOA;
import com.example.cab302project.Model.ViewingUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class HomeController {
    public Button search;
    public ImageView profileImageView;
    @FXML
    private TextField accountSearchField;

    @FXML
    private ListView<String> accountListView;
    SQLiteUserDOA sqLiteUserDOA = new SQLiteUserDOA();

    private ObservableList<String> accounts = sqLiteUserDOA.getAllUsers(LoginController.Session.getLoggedInUser());
    private ObservableList<String> noSearch = FXCollections.observableArrayList();


    public void initialize() throws SQLException {
        accountListView.setItems(noSearch);
        accountSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                // Display noSearch, which is an empty list, when search field is empty
                accountListView.setItems(noSearch);
            } else {
                // Filter based on search term
                accountListView.setItems(accounts.filtered(account -> account.toLowerCase().contains(newValue.toLowerCase())));
            }
        });

        // Set onMouseClicked event listener
        accountListView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) { // Double-click
                String selectedUser = accountListView.getSelectionModel().getSelectedItem();
                if (selectedUser != null) {
                    try {
                        // Handle the row click and navigate to the next page
                        handleRowClick();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        // Set profile picture for the logged-in user
        LoginController.Session session = new LoginController.Session();
        Image image = sqLiteUserDOA.getProfilePicture(session.getLoggedInUser());
        if (image != null) {
            profileImageView.setImage(image);
        }
    }

    // Method to handle the row click event
    private void handleRowClick() throws IOException {
        ViewingUser.setSelectedUser(accountListView.getSelectionModel().getSelectedItem());
        Stage window = (Stage) search.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Other_Profile_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    // Redirects user to the home page
    public void HomeButton(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) search.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Home_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    @FXML
    public void logOut() throws IOException {
        LoginController.Session.clearSession();
        Stage window = (Stage) search.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    // Redirects user to the post creation page
    @FXML
    protected void onNextButtonClick() throws IOException {
        Stage window = (Stage) search.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Create_Post.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    public void pageRedirect(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) search.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

}
