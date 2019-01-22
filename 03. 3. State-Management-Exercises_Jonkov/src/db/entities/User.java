package db.entities;

import java.util.UUID;

public class User {
    private String id;

    private String email;

    private String password;


    public User(String userName, String password) {
        this.setId();
        this.setEmail(userName);
        this.setPassword(password);
    }

    public String getId() {
        return this.id;
    }

    private void setId() {
        this.id = UUID.randomUUID().toString();
    }

    public String getEmail() {
        return this.email;
    }

    private void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return this.password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s", this.getId(), this.getEmail(), this.getPassword());
    }
}
