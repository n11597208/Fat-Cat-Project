package com.example.cab302project.Controller;

import com.example.cab302project.Model.Post;
import com.example.cab302project.Model.SQLitePostDOA;

import static com.example.cab302project.Controller.LoginController.Session.getLoggedInUser;

public class PostManager {
    private static SQLitePostDOA postDAO;
    public PostManager(SQLitePostDOA postDOA) {
        this.postDAO = postDOA;
    }
    public static void addPost(Post post) {
        postDAO.addPost(post, getLoggedInUser());
    }
    public void deletePost(Post post) {
        postDAO.deletePost(post);
    }
}
