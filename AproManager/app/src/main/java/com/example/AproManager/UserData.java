package com.example.AproManager;

public class UserData {
    private String token;
    private String name;
    private String email;
    private String profileUri;

    public UserData(String token, String name, String email, String profileUri) {
        this.token = token;
        this.name = name;
        this.email = email;
        this.profileUri = profileUri;
    }

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }
}
