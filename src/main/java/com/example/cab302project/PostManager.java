package com.example.cab302project;

import java.sql.SQLException;

import static com.example.cab302project.LoginController.Session.getLoggedInUser;

public class PostManager {
    private static SQLitePostDOA postDAO;
    public PostManager(SQLitePostDOA postDOA) {
        this.postDAO = postDOA;
    }
    public static void addPost(Post post) {
        postDAO.addPost(post, getLoggedInUser());
    }
    public void deletePost(Integer postId) {
        postDAO.deletePost(postId);
    }
    public void updatePost(Post post) throws SQLException {
        postDAO.updatePost(post, post.getId());
    }
}
