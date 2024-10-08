package com.example.cab302project.Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class SQLiteConnection {
    private static Connection instance = null;

    private SQLiteConnection() {
        String url = "jdbc:sqlite:database.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            new SQLiteConnection();
        }
        return instance;
    }
}
