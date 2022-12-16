package me.omigo.zoomanager;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String zoneName;
    @OneToMany(mappedBy = "zone")
    private Set<Animal> animalSet;

    public Zone(String zoneName) {
        animalSet = new HashSet<>();
        this.zoneName = zoneName;
    }

    public Zone() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }
}
