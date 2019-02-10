package org.softuni.java_ee_block.data.models;

import java.time.LocalDateTime;

public class Order {
    private User client;
    private Cat cat;
    private LocalDateTime madeOn;

    public Order(User client, Cat cat ) {
        this.setClient(client);
        this.setCat(cat);
        this.setMadeOn();
    }

    public User getClient() {
        return this.client;
    }

    private void setClient(User client) {
        this.client = client;
    }

    public Cat getCat() {
        return this.cat;
    }

    private void setCat(Cat cat) {
        this.cat = cat;
    }

    public LocalDateTime getMadeOn() {
        return this.madeOn;
    }

    private void setMadeOn() {
        this.madeOn = LocalDateTime.now();
    }
}
