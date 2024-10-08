package com.example.cab302project.Model;

public class Comment {
    private int commentId;
    private int postId;
    private String text;
    private String timestamp;

    public Comment(int commentId, int postId, String text, String timestamp) {
        this.commentId = commentId;
        this.postId = postId;
        this.text = text;
        this.timestamp = timestamp;
    }

    public int getCommentId() {
        return commentId;
    }

    public int getPostId() {
        return postId;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
