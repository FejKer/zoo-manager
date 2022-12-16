package me.omigo.zoomanager;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Zone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;        //TODO unique
    @OneToMany(mappedBy = "zone")
    @JsonIgnore
    private Set<Animal> animalSet;

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
}
