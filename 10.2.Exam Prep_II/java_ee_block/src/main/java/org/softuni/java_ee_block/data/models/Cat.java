package org.softuni.java_ee_block.data.models;

public class Cat {
    private String name;

    private String breed;

    private String color;

    private Integer numberOfLegs;

    private Integer viewsCount;

    private User creator;

    public Cat(String name, String breed, String color, Integer numberOfLegs, User creator) {
        this.setName(name);
        this.setBreed(breed);
        this.setColor(color);
        this.setNumberOfLegs(numberOfLegs);
        this.setCreator(creator);
        this.setViewsCount();
    }

    public Integer getViewsCount() {
        return this.viewsCount;
    }

    private void setViewsCount() {
        this.viewsCount = 0;
    }

    public Integer increaseViewsCount(){
        return ++this.viewsCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getNumberOfLegs() {
        return numberOfLegs;
    }

    public void setNumberOfLegs(Integer numberOfLegs) {
        this.numberOfLegs = numberOfLegs;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
