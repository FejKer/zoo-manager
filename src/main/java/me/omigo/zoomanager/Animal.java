package me.omigo.zoomanager;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public abstract class Animal {
    final static int ELEPHANT_REQUIRED_FOOD = 20;
    final static int LION_REQUIRED_FOOD = 11;
    final static int RABBIT_REQUIRED_FOOD = 4;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String species;
    @ManyToOne
    private Zone zone;
    private int requiredFood;

    public Animal(String name, String species, int requiredFood, Zone zone) {
        this.name = name;
        this.species = species;
        this.requiredFood = requiredFood;
        this.zone = zone;
    }

    public Animal() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public int getRequiredFood() {
        return requiredFood;
    }

    public void setRequiredFood(int requiredFood) {
        this.requiredFood = requiredFood;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Animal animal))
            return false;
        return Objects.equals(this.id, animal.getId()) && Objects.equals(this.name, animal.getName())
                && Objects.equals(this.species, animal.getSpecies());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.species);
    }

    @Override
    public String toString() {
        return "Animal{" + "id=" + this.id + ", name='" + this.name + '\'' + ", species='" + this.species + '\'' + ", required food='" + this.requiredFood + ", zone='" + this.zone.getZoneName() + '\'' + '}';
    }
}
