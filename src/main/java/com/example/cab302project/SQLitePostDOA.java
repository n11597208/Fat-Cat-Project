package com.example.cab302project;

import java.sql.*;

public class SQLitePostDOA {
    private Connection connection;

    // Constructor
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

    private void createTable() {
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
                    + "postImage BLOB NULL," // Allow postImage to be NULL
                    + "FOREIGN KEY(userId) REFERENCES users(id)"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void addPost(Post post, String userName) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO posts (userId, postTitle, postDescription, carMake, carModel, postLocation) VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, userName);
            statement.setString(2, post.getTitle());
            statement.setString(3, post.getDescription());
            statement.setString(4, post.getMake());
            statement.setString(5, post.getModel());
            statement.setString(6, post.getLocation());
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
    void updatePost(Post post, String userName) throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE posts SET postTitle = ?, postDescription = ?, carMake = ?, carModel = ?, postLocation = ? WHERE userId = ?",
                    Statement.RETURN_GENERATED_KEYS);

            // Set the parameters (1-based index)
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getMake());
            statement.setString(4, post.getModel());
            statement.setString(5, post.getLocation());
            statement.setString(6, userName); // Assuming userName corresponds to userId

            // Execute the update
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Override
    public void deletePost(Post post) {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM posts WHERE id = ?");
            statement.setInt(1, post.getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Post getPost(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM post WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String title = resultSet.getString("postTitle");
                String description = resultSet.getString("postDescription");
                String make = resultSet.getString("carMake");
                String model = resultSet.getString("carModel");
                String location = resultSet.getString("postLocation");
                Blob image = resultSet.getBlob("postImage");
                Post post = new Post(title, description, make, model, location, image.toString());
                post.setId(id);
                return post;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    // Other methods...
}
