package me.omigo.zoomanager.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true)    //making name unique on database level
    private String name;
    @OneToMany(mappedBy = "zone")
    @JsonIgnore                         //@JsonIgnore to NOT display all animals info this zone has
    private Set<Animal> animalSet;      //one-to-many relation with animals

    public Zone(String name) {
        animalSet = new HashSet<>();
        this.name = name;
    }

    public Zone() {

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

    public Set<Animal> getAnimalSet() {
        return animalSet;
    }

    public void setAnimalSet(Set<Animal> animalSet) {
        this.animalSet = animalSet;
    }

    @Override
    public String toString() {
        return "Zone{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
