package com.example.cab302project.Model;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private Integer followers;
    private Integer numberOfPosts;
    private byte[] profilePicture;
    private String description;

    //  Constructor to initialize a Post object with the provided parameters. Sets the initial rating, number of comments, and number of shares to zero.
    public User (int id, String firstName, String lastName, String email, String userName, Integer followers, Integer numberOfPosts, String description) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.followers = followers;
        this.numberOfPosts = numberOfPosts;
//        this.profilePicture = profilePicture;
        this.description = description;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Integer getFollowers() {
        return followers;
    }
    public void setFollowers(Integer followers) {
        this.followers = followers;
    }
    public Integer getNumberOfPosts() {
        return numberOfPosts;
    }
    public void setNumberOfPosts(Integer numberOfPosts) {
        this.numberOfPosts = numberOfPosts;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
