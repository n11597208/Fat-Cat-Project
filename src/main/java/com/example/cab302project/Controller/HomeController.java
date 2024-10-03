package com.example.cab302project.Controller;

import com.example.cab302project.HelloApplication;
import com.example.cab302project.Model.Post;
import com.example.cab302project.Model.SQLitePostDOA;
import com.example.cab302project.Model.SQLiteUserDOA;
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

    private ObservableList<String> accounts = FXCollections.observableArrayList("Account1", "Account2", "Account3", "Account4");
    SQLiteUserDOA sqLiteUserDOA = new SQLiteUserDOA();
    public void initialize() throws SQLException {
        accountListView.setItems(accounts);
        accountSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
                    accountListView.setItems(accounts.filtered(account -> account.toLowerCase().contains(newValue.toLowerCase())));
                });
        LoginController.Session session = new LoginController.Session();
        Image image = sqLiteUserDOA.getProfilePicture(session.getLoggedInUser());
        if (image != null) {
            profileImageView.setImage(image);
        }
        String currentUser = session.getLoggedInUser();
    }

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
    //redirects user to the post creation page
    @FXML
    protected void onNextButtonClick() throws IOException {
        Stage window = (Stage) search.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Create_Post.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    public void HandleSearch(ActionEvent actionEvent) {
    }
}
