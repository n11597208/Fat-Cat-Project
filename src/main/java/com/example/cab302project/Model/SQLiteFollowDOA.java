package com.example.cab302project.Model;

import java.sql.*;

public class SQLiteFollowDOA {
    private static Connection connection;

    public SQLiteFollowDOA() {
        connection = SqliteConnection.connect();
        createTable();
    }

    private void enableForeignKeys() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("PRAGMA foreign_keys = ON;");
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS followers ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "account STRING NOT NULL,"
                    + "follower STRING NOT NULL,"
                    + "FOREIGN KEY(account) REFERENCES users(username),"
                    + "FOREIGN KEY(follower) REFERENCES users(username)"
                    + ")";
            statement.execute(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertFollower(String account, String follower) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO followers (account,follower) VALUES (?, ?)"
            );

            statement.setString(1, account);
            statement.setString(2, follower);
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Boolean isFollower(String account, String follower) throws SQLException {
        String query = "SELECT follower FROM followers WHERE account = ? AND follower = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, account);
            statement.setString(2, follower);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();  // Return true if a record is found, meaning they are a follower
            }
        }
    }
    public void unfollow(String account, String follower) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM followers WHERE account = ? AND follower = ?"
        );
        statement.setString(1, account);
        statement.setString(2, follower);
        statement.executeUpdate();
    }


}
