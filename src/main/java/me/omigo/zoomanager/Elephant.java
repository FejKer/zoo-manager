package me.omigo.zoomanager;

import jakarta.persistence.Entity;

@Entity
public class Elephant extends Animal {

    public Elephant(String name, Zone zone) {
        super(name, "Elephant", ELEPHANT_REQUIRED_FOOD, zone);
    }

    public Elephant() {

    }
}
