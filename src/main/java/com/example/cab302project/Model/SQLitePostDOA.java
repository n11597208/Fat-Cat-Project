package com.example.cab302project.Model;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SQLitePostDOA {
    private static Connection connection;


    public SQLitePostDOA() {
        connection = SqliteConnection.connect();
        createTable();
    }

    private void enableForeignKeys() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON;");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// Method to create posts table in database with all of the relevant fields and their data types
    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS posts ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "userId INTEGER NOT NULL,"
                    + "postTitle VARCHAR NOT NULL,"
                    + "postDescription VARCHAR NOT NULL,"
                    + "carMake VARCHAR NOT NULL,"
                    + "carModel VARCHAR NOT NULL,"
                    + "postLocation VARCHAR NULL,"
                    + "postImage BLOB NULL,"
                    + "rating INTEGER NULL,"
                    + "numberOfComments INTEGER NULL,"
                    + "numberOfShares INTEGER NULL,"
                    + "FOREIGN KEY(userId) REFERENCES users(id)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
// Method to add new post. Takes a post and username of poster as parameters and inserts post details with the post author in the posts table
    public void addPost(Post post, String userName) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO posts (userId, postTitle, postDescription, carMake, carModel, postLocation, postImage, rating, numberOfComments, numberOfShares) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            if (post.getTitle() == "" || post.getDescription() == "" || post.getMake() == "" || post.getModel() == "" || post.getLocation() == "") {
                return;
            }
            else {
                statement.setString(1, userName);
                statement.setString(2, post.getTitle());
                statement.setString(3, post.getDescription());
                statement.setString(4, post.getMake());
                statement.setString(5, post.getModel());
                statement.setString(6, post.getLocation());
                statement.setBytes(7, post.getPostImage());
                statement.setInt(8, 0);
                statement.setInt(9, 0);
                statement.setInt(10, 0);
                statement.executeUpdate();
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    post.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
//    @Override
    //Takes user inputs and updates post field where post id is the current post the user is interacting in
public void updatePost(Post post, int postId) throws SQLException {
    String sql = "UPDATE posts SET postTitle = ?, postDescription = ?, carMake = ?, carModel = ?, postLocation = ? WHERE id = ?";
    if (post.getTitle() == "" || post.getDescription() == "" || post.getMake() == "" || post.getModel() == "" || post.getLocation() == "") {
        return;
    }
    else {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getMake());
            statement.setString(4, post.getModel());
            statement.setString(5, post.getLocation());
            statement.setInt(6, postId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
    public void updateUserPost(String CurrentUserName, String NewUserName) throws SQLException {
        String sql = "UPDATE posts SET userId = ? WHERE userId = ?";
        {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, NewUserName);
                statement.setString(2, CurrentUserName);
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

// Deletes the field in the post field that the user is currently interacting with
//    @Override
    public void deletePost(Integer postId) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM posts WHERE id = ?");
            statement.setInt(1,postId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //gets the number of posts that a user has created requires the users username
    public int getNumPosts(String userName) throws SQLException {
        int numPosts = 0;
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM posts WHERE userID = ?");
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            numPosts = resultSet.getInt(1);
        }
        resultSet.close();
        statement.close();
        return numPosts;
    }
    public void incrementFollowers(Integer postId) {
        String selectQuery = "SELECT numberOfComments FROM posts WHERE id = ?";
        String updateQuery = "UPDATE posts SET numberOfComments = ? WHERE id = ?";

        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setInt(1, postId);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    int comments = resultSet.getInt("numberOfComments");
                    comments += 1;

                    // Update the followers count
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setInt(1, comments);
                        updateStatement.setInt(2, postId);
                        updateStatement.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error incrementing followers: " + e.getMessage(), e);
        }
    }
    public Integer getFollowers(Integer postId) {
        String selectQuery = "SELECT numberOfComments FROM posts WHERE id = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setInt(1, postId);
            try (ResultSet rs = selectStatement.executeQuery()) {  // Directly use the returned ResultSet
                int comments = 0;
                if (rs.next()) {
                    comments = rs.getInt("numberOfComments");
                }
                return comments;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving number of comments: " + e.getMessage(), e);
        }
    }


    //returns all of the post information for a specified post id
public static Post getPost(int id) {
        String sql = "SELECT * FROM posts WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Integer postId = resultSet.getInt("id");
                    String title = resultSet.getString("postTitle");
                    String description = resultSet.getString("postDescription");
                    String userID = resultSet.getString("userID");
                    String make = resultSet.getString("carMake");
                    String model = resultSet.getString("carModel");
                    String location = resultSet.getString("postLocation");
                    byte[] imageBytes = resultSet.getBytes("postImage");
                    Integer rating = resultSet.getInt("rating");
                    Integer numberComments = resultSet.getInt("numberOfComments");
                    Integer numberShares = resultSet.getInt("numberOfShares");

                    Post post = new Post(title, description, userID, make, model, location, imageBytes, rating, numberComments, numberShares);
                    post.setId(postId);
                    return post;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
// returns a list of all of the posts created by a user
    public static List<Post> getPostsByAuthor(String author) {
        List<Post> posts = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM posts WHERE userId = ?");
            statement.setString(1, author);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                Integer postId = resultSet.getInt("id");
                String title = resultSet.getString("postTitle");
                String description = resultSet.getString("postDescription");
                String make = resultSet.getString("carMake");
                String model = resultSet.getString("carModel");
                String location = resultSet.getString("postLocation");
                byte[] imageBytes = resultSet.getBytes("postImage");
                Integer rating = resultSet.getInt("rating");
                Integer numberComments = resultSet.getInt("numberOfComments");
                Integer numberShares = resultSet.getInt("numberOfShares");


                Post post = new Post(title, description, author, make, model, location, imageBytes, rating, numberComments, numberShares);
                post.setId(postId);

                posts.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }
    public void incrementFollowers(String username){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * followers FROM user WHERE userID = ?");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int followers = resultSet.getInt("followers");
                followers += 1;
                PreparedStatement statement2 = connection.prepareStatement("UPDATE followers SET followers = ? WHERE userID = ?");
                statement2.setInt(1, followers);
                statement2.setString(2, username);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void commentTable() {
        String sql = "CREATE TABLE IF NOT EXISTS comments (" +
                "commentId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "postId INTEGER NOT NULL," +
                "commentText TEXT NOT NULL," +
                "timestamp TEXT NOT NULL," +
                "FOREIGN KEY(id) REFERENCES posts(id)" +
                ");";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating comments table: " + e.getMessage());
        }
    }




}

