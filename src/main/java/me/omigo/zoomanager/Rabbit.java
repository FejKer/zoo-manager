package me.omigo.zoomanager;

import jakarta.persistence.Entity;

@Entity
public class Rabbit extends Animal {

    public Rabbit(String name, Zone zone) {
        super(name, "Rabbit", RABBIT_REQUIRED_FOOD, zone);
    }

    public Rabbit() {

    }
}
