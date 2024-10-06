package com.example.cab302project.Model;

public class ViewingUser {
    private static String userName;
    public static void setSelectedUser(String username) {
        userName = username;
    }

    public static String getSelectedUser() {
        return userName;
    }

    public static void clearSelectedUser() {
        userName = null;
    }
}


