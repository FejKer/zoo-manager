package me.omigo.zoomanager;

import jakarta.persistence.Entity;

@Entity
public class Lion extends Animal{

    public Lion(String name, Zone zone) {
        super(name, "Lion", LION_REQUIRED_FOOD, zone);
    }

    public Lion() {

    }
}
