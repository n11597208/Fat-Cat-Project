package com.example.cab302project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class ProfileController {

    @FXML
    private VBox postsContainer;

    @FXML
    private Label welcomeText1;

    @FXML
    private Label welcomeText2;

    @FXML
    private Label welcomeText3;

    @FXML
    private Label welcomeText4;

    @FXML
    private Label welcomeText5;

    @FXML
    private Region spacer;

    @FXML
    private Region spacer2;

    @FXML
    private Button nextButton;

    private LoginController.Session session;

    @FXML
    public void initialize() {

        session = new LoginController.Session();
        changeLabelText();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        String currentUser = session.getLoggedInUser();
        List<Post> posts = SQLitePostDOA.getPostsByAuthor(currentUser);


        for (Post post : posts) {
            VBox postBox = createPostBox(post);
            postsContainer.getChildren().add(postBox);
        }
    }

    @FXML
    protected void onNextButtonClick() throws IOException {
        Stage stage = (Stage) nextButton.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Create_Post.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    private void changeLabelText() {

        String currentUser = session.getLoggedInUser();
        welcomeText1.setText(currentUser);
        welcomeText4.setText("0");
        welcomeText5.setText("0");
    }

    private VBox createPostBox(Post post) {

        VBox postBox = new VBox();
        postBox.setAlignment(Pos.CENTER_LEFT);
        postBox.setSpacing(10);
        postBox.setPadding(new Insets(10));
        postBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");


        ImageView postImageView = new ImageView();
        postImageView.setFitWidth(150);
        postImageView.setFitHeight(150);

        if (post.getPostImage() != null) {
            byte[] imageBytes = post.getPostImage();
            System.out.println("Image size: " + imageBytes.length);
            postImageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
        } else {
            System.out.println("No image for this post");
        }

        Label postTitle = new Label(post.getTitle());
        postTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");


        Label postDescription = new Label(post.getDescription());
        postDescription.setWrapText(true);


        HBox detailsBox = new HBox();
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        detailsBox.setSpacing(10);


        Label ratingLabel = new Label("Rating: " + post.getRating());


        Label commentLabel = new Label("Comments: " + post.getNumComments());


        Label shareLabel = new Label("Shares: " + post.getNumshares());


        detailsBox.getChildren().addAll(ratingLabel, commentLabel, shareLabel);
        HBox controlBox = new HBox();
        controlBox.setAlignment(Pos.CENTER_LEFT);
        controlBox.setSpacing(10);

        Button editButton = new Button("Edit");

        Button deleteButton = new Button("Delete" );
//        deleteButton.setOnAction(event -> {
////            PostManager.deletePost(post.getPostID());
//        });
        controlBox.getChildren().addAll(editButton, deleteButton);


        postBox.getChildren().addAll(postImageView, postTitle, postDescription, detailsBox, controlBox);

        return postBox;
    }
//    private void onDelete() {
//        // Get the selected contact from the list view
//        Post selectedPost = contactsListView.getSelectionModel().getSelectedItem();
//        if (selectedPost != null) {
//            PostManager.deletePost(selectedPost);
//        }

}