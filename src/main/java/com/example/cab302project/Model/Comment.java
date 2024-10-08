package com.example.cab302project.Model;

public class Comment {
    private int commentId;
    private int postId;
    private String text;
    private String timestamp;
    private String author;

    public Comment( int postId, String text, String timestamp, String author) {
        this.postId = postId;
        this.text = text;
        this.timestamp = timestamp;
        this.author = author;
    }
//d
    public int getCommentId() {
        return commentId;
    }
    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getPostId() {
        return postId;
    }
    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}
