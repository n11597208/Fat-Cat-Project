package com.example.cab302project.Model;

public class Post {
    // All post attributes
    private int id;
    private String title;
    private String description;
    private String author;
    private String model;
    private String make;
    private String location;
    private byte[] postImage;
    private Integer rating;
    private Integer numComments;
    private Integer numshares;

    //  Constructor to initialize a Post object with the provided parameters. Sets the initial rating, number of comments, and number of shares to zero.
    public Post(String title, String description, String author, String model, String make, String location, byte[] image, Integer rating, Integer numComments, Integer numshares) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.model = model;
        this.make = make;
        this.location = location;
        this.postImage = image;
        this.rating = 0;
        this.numComments = 0;
        this.numshares = 0;
    }
    // Getter and setter methods for each field

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getPostImage() {
        return postImage;
    }

    public void setPostImage(byte[] postImage) {
        this.postImage = postImage;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getNumComments() {
        return numComments;
    }

    public void setNumComments(Integer numComments) {
        this.numComments = numComments;
    }

    public Integer getNumshares() {
        return numshares;
    }

    public void setNumshares(Integer numshares) {
        this.numshares = numshares;
    }
}
