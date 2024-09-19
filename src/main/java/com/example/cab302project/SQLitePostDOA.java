package com.example.cab302project;

import java.sql.*;
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

    public void addPost(Post post, String userName) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO posts (userId, postTitle, postDescription, carMake, carModel, postLocation, postImage, rating, numberOfComments, numberOfShares) VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS
            );
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Override
public void updatePost(Post post, int postId) throws SQLException {
    String sql = "UPDATE posts SET postTitle = ?, postDescription = ?, carMake = ?, carModel = ?, postLocation = ? WHERE id = ?";

    // Use try-with-resources for automatic resource management
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        // Set parameters in the prepared statement
        statement.setString(1, post.getTitle());
        statement.setString(2, post.getDescription());
        statement.setString(3, post.getMake());
        statement.setString(4, post.getModel());
        statement.setString(5, post.getLocation());
        statement.setInt(6, postId);

        // Execute the update
        statement.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace(); // Log the exception
        throw e; // Optionally rethrow the exception if needed
    }
}


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

}

