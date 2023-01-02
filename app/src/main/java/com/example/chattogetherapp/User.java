package com.example.chattogetherapp;

public class User {
    private String Username ;
    private String Email ;
    private String ProfilePicture ;

    public User(){

    }

    public User(String username, String email, String profilePicture) {
        Username = username;
        Email = email;
        ProfilePicture = profilePicture;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }
}
