package com.example.cab302project.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;  // Import LocalDateTime
import java.time.format.DateTimeFormatter; // Import DateTimeFormatter

public class SQLiteCommentDAO {
    private Connection connection;

    public SQLiteCommentDAO() {
        connection = SqliteConnection.connect(); // Assuming you have a method to establish a connection
        createCommentTable(); // Create comments table if it doesn't exist
    }

    // Method to create comments table in the database
    public void createCommentTable() {
        String sql = "CREATE TABLE IF NOT EXISTS comments (" +
                "commentId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "postId INTEGER NOT NULL," +
                "commentText TEXT NOT NULL," +
                "timestamp TEXT NOT NULL," +
                "author TEXT NOT NULL," +
                "FOREIGN KEY(postId) REFERENCES posts(id)" +
                "FOREIGN KEY(author) REFERENCES users(username)" +
                ");";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating comments table: " + e.getMessage());
        }
    }

    // Method to add a new comment to a post
    public void addComment( Comment comment) throws SQLException {
        String sql = "INSERT INTO comments (postId, commentText, timestamp, author) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, comment.getPostId());
            statement.setString(2, comment.getText());
            statement.setString(3, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
            statement.setString(4, comment.getAuthor());
            statement.executeUpdate();
        }
    }

    // Method to get comments by post ID
    public List<Comment> getCommentsByPostId(int postId) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * FROM comments WHERE postId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, postId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int commentId = resultSet.getInt("commentId");
                    String commentText = resultSet.getString("commentText");
                    String timestamp = resultSet.getString("timestamp");
                    String author = resultSet.getString("author");
                    comments.add(new Comment( postId, commentText, timestamp, author) ); // Adjust constructor as needed
                }
            }
        }
        return comments;
    }

    // Method to delete a comment by ID
    public void deleteComment(int commentId) throws SQLException {
        String sql = "DELETE FROM comments WHERE commentId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, commentId);
            statement.executeUpdate();
        }
    }

    // Optional: Method to update a comment by ID
    public void updateCommentUser(String CurrentUser, String newUserName) throws SQLException {
        String sql = "UPDATE comments SET author = ? WHERE author = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, newUserName);
            statement.setString(2, CurrentUser);
            statement.executeUpdate();
        }
    }
}
