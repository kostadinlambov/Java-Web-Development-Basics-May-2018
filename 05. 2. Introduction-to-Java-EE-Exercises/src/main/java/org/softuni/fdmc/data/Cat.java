package org.softuni.fdmc.data;

public class Cat {
    private String name;
    private String breed;
    private String color;
    private int numberOfLegs;

    public Cat(String name, String breed, String color, int numberOfLegs) {
        this.setName(name);
        this.setBreed(breed);
        this.setColor(color) ;
        this.setNumberOfLegs(numberOfLegs);
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return this.breed;
    }

    private void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return this.color;
    }

    private void setColor(String color) {
        this.color = color;
    }

    public int getNumberOfLegs() {
        return this.numberOfLegs;
    }

    private void setNumberOfLegs(int numberOfLegs) {
        this.numberOfLegs = numberOfLegs;
    }



    @Override
    public String toString() {
        return String.format("Name: %s, Breed: %s, Color: %s, NumOfLegs: %d\n\r", this.getName(), this.getBreed(), this.getColor(), this.getNumberOfLegs());
    }
}
