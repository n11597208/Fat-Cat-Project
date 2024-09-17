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
    private VBox postsContainer;  // Make sure this matches your FXML fx:id for the VBox

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
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        String currentUser = session.getLoggedInUser();
        List<Post> posts = SQLitePostDOA.getPostsByAuthor(currentUser);  // Get posts from your DB or service

        // Dynamically add each post to the VBox inside the ScrollPane
        for (Post post : posts) {
            VBox postBox = createPostBox(post);  // Dynamically create a post layout
            postsContainer.getChildren().add(postBox);  // Add the post layout to the container
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
        // Get the current logged-in user and set the label text
        String currentUser = session.getLoggedInUser();
        welcomeText1.setText(currentUser);
    }

    private VBox createPostBox(Post post) {
        // Create VBox for individual post
        VBox postBox = new VBox();
        postBox.setAlignment(Pos.CENTER_LEFT);
        postBox.setSpacing(10);
        postBox.setPadding(new Insets(10));
        postBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");

        // Add an image for the post (replace logo.png with post image)
        ImageView postImageView = new ImageView();
        postImageView.setFitWidth(150);
        postImageView.setFitHeight(150);
//         If post has an image, use it; otherwise, use a default image
        if (post.getPostImage() != null) {
            byte[] imageBytes = post.getPostImage();
            System.out.println("Image size: " + imageBytes.length);  // Debug to check the image size
            postImageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
        } else {
            System.out.println("No image for this post");
            // Optionally, set a placeholder image if no image is available
//            postImageView.setImage(new Image("path/to/placeholder.png"));
        }

        // Post title
        Label postTitle = new Label(post.getTitle());
        postTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Post description (optional)
        Label postDescription = new Label(post.getDescription());
        postDescription.setWrapText(true);

        // Add Star Rating, Comments, Shares using HBox
        HBox detailsBox = new HBox();
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        detailsBox.setSpacing(10);

        // Add Star Rating
        Label ratingLabel = new Label("Rating: " + post.getRating());

        // Add Comments
        Label commentLabel = new Label("Comments: " + post.getNumComments());

        // Add Shares
        Label shareLabel = new Label("Shares: " + post.getNumshares());

        // Add labels to HBox
        detailsBox.getChildren().addAll(ratingLabel, commentLabel, shareLabel);

        // Add components to VBox
        postBox.getChildren().addAll(postImageView, postTitle, postDescription, detailsBox);

        return postBox;
    }
}
