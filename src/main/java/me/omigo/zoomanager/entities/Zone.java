package me.omigo.zoomanager.entities;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Zone {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int animalsCount = 0;
  private int zoneRequiredFood = 0;

  @Column(unique = true) // making name unique on database level
  private String name;

  @OneToMany(mappedBy = "zone")
  private Set<Animal> animalSet = new HashSet<>(); // one-to-many relation with animals

  public Zone(String name) {
    this.name = name;
  }

  public Zone() {}

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

  public int getAnimalsCount() {
    return animalSet.size();
  }

  public void setAnimalsCount(int animalsCount) {
    this.animalsCount = animalsCount;
  }

  public int getZoneRequiredFood() {
    for (Animal a : animalSet) {
      zoneRequiredFood += a.getRequiredFood();
    }
    return zoneRequiredFood;
  }

  public void setZoneRequiredFood(int zoneRequiredFood) {
    this.zoneRequiredFood = zoneRequiredFood;
  }

  public Set<Animal> getAnimalSet() {
    return animalSet;
  }

  public void setAnimalSet(Set<Animal> animalSet) {
    this.animalSet = animalSet;
  }

  @Override
  public String toString() {
    return "Zone{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
