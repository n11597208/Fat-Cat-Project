package com.example.cab302project.Controller;

import com.example.cab302project.HelloApplication;
import com.example.cab302project.Model.Post;
import com.example.cab302project.Model.SQLitePostDOA;
import com.example.cab302project.Model.SQLiteUserDOA;
import com.example.cab302project.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProfileController {

    public MenuItem logout;
    public ImageView profileImageView2;
    public Text descriptionText;
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
    private Button followButton;
    @FXML
    private ImageView profileImageView;
    @FXML
    private VBox leftSection;  // The section that takes 25%

    @FXML
    private VBox rightSection; // The section that takes 75%

    SQLitePostDOA sqLitePostDOA = new SQLitePostDOA();
    SQLiteUserDOA sqLiteUserDOA = new SQLiteUserDOA();

    private LoginController.Session session;
    public static int id;

    public void setId(int newId) {
        id = newId;
    }

    public static int getId() {
        return id;
    }

    // gets the current user who is logged in, and initialises list of posts
    @FXML
    public void initialize() throws SQLException {
        leftSection.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                // Bind the width properties to maintain 25%-75% ratio
                leftSection.prefWidthProperty().bind(newScene.widthProperty().multiply(0.25));
                rightSection.prefWidthProperty().bind(newScene.widthProperty().multiply(0.75));
            }
        });
        session = new LoginController.Session();
        changeLabelText();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        Image image = sqLiteUserDOA.getProfilePicture(session.getLoggedInUser());
        if (image != null) {
            profileImageView.setImage(image);
            profileImageView2.setImage(image);
        }
        String currentUser = session.getLoggedInUser();
        List<Post> posts = SQLitePostDOA.getPostsByAuthor(currentUser);
        for (Post post : posts) {
            VBox postBox = createPostBox(post);
            postsContainer.getChildren().add(postBox);
        }
    }

    @FXML
    public void logOut() throws IOException {
        LoginController.Session.clearSession();
        Stage window = (Stage) welcomeText1.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    // redirects user to the post creation page
    @FXML
    protected void onNextButtonClick() throws IOException {
        Stage window = (Stage) welcomeText1.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Create_Post.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    // sets the username label to current user and number of posts label to the total posts the user has made
    private void changeLabelText() throws SQLException {
        User user = sqLiteUserDOA.getUser(session.getLoggedInUser());
        String currentUser = session.getLoggedInUser();
        welcomeText1.setText(currentUser);
        welcomeText4.setText("0");
        welcomeText5.setText(String.valueOf(sqLitePostDOA.getNumPosts(currentUser)));
        descriptionText.setText(user.getDescription());
    }

    // Dynamically adds all of the details for a post based on the list of Posts created by the user
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
            postImageView.setImage(new Image(new ByteArrayInputStream(imageBytes)));
        }

        Label postTitle = new Label(post.getTitle());
        postTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label postDescription = new Label(post.getDescription());
        postDescription.setWrapText(true);

        HBox detailsBox = new HBox();
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        detailsBox.setSpacing(10);
        Label ratingLabel = new Label("Rating: " + post.getRating());
        Button commentButton = new Button("Comments: " + post.getNumComments());
        commentButton.setOnAction(e -> {
            displayComments(post);
        });

        Label shareLabel = new Label("Shares: " + post.getNumshares());
        detailsBox.getChildren().addAll(ratingLabel, commentButton, shareLabel);

        HBox controlBox = new HBox();
        controlBox.setAlignment(Pos.CENTER_LEFT);
        controlBox.setSpacing(10);

        Button editButton = new Button("Edit");
        editButton.setOnAction(e -> {
            try {
                int id = post.getId();
                setId(id);
                Stage window = (Stage) editButton.getScene().getWindow();
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Edit_Post.fxml"));
                Parent root = fxmlLoader.load();
                Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
                window.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            int postId = post.getId();
            sqLitePostDOA.deletePost(postId);
            Stage window = (Stage) deleteButton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
            Parent root;
            try {
                root = fxmlLoader.load();
                Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
                window.setScene(scene);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        // Add comment button and commentsBox
        Button addCommentButton = new Button("Add Comment");
        VBox commentsBox = new VBox();
        commentsBox.setSpacing(10);
        commentsBox.setPadding(new Insets(5, 0, 0, 0)); // Padding for comments

        addCommentButton.setOnAction(e -> {
            // Prompt the user for a comment
            TextInputDialog commentDialog = new TextInputDialog();
            commentDialog.setTitle("Add Comment");
            commentDialog.setHeaderText(null);
            commentDialog.setContentText("Enter your comment:");

            // Show the dialog and capture the user's input
            Optional<String> commentResult = commentDialog.showAndWait();
            commentResult.ifPresent(comment -> {
                if (comment.trim().isEmpty()) {
                    // Show a warning if the comment is empty
                    Alert emptyAlert = new Alert(Alert.AlertType.WARNING);
                    emptyAlert.setTitle("Warning");
                    emptyAlert.setHeaderText(null);
                    emptyAlert.setContentText("Comment cannot be empty!");
                    emptyAlert.showAndWait();
                } else {
                    // Add the comment to the commentsBox
                    Label commentLabel = new Label(comment);
                    commentLabel.setWrapText(true); // Wrap text for long comments
                    commentsBox.getChildren().add(commentLabel);
                }
            });
        });

        controlBox.getChildren().addAll(editButton, deleteButton, addCommentButton);

        // Add all components to postBox
        postBox.getChildren().addAll(postImageView, postTitle, postDescription, detailsBox, controlBox, commentsBox);
        return postBox;
    }

    // New method to display comments
    private void displayComments(Post post) {
        Alert commentsAlert = new Alert(Alert.AlertType.INFORMATION);
        commentsAlert.setTitle("Comments for: " + post.getTitle());
        commentsAlert.setHeaderText(null);

        // You can retrieve and display comments related to the post here
        // For demonstration, let's just display a placeholder message
        String comments = "Comments for post: " + post.getTitle(); // Replace with actual comments fetching logic
        commentsAlert.setContentText(comments);

        commentsAlert.showAndWait();
    }

    public void HomeButton(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) welcomeText1.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Home_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }

    public void pageRedirect(ActionEvent actionEvent) throws IOException {
        Stage window = (Stage) welcomeText1.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Profile_UI.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
        window.setScene(scene);
    }
}
