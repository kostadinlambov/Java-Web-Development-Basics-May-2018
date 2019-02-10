package org.softuni.fdmc.data.models;

public class User {
    private String username;

    private String password;

    private String isAdmin;

    public User(String username, String password, String isAdmin) {
        this.setUsername(username);
        this.setPassword(password);
        this.setAdmin(isAdmin);
    }

    public User() {
    }

    public String getUsername() {
        return this.username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String isAdmin() {
        return this.isAdmin;
    }

    private void setAdmin(String admin) {
        this.isAdmin = admin;
    }
}
