package me.omigo.zoomanager.entities;

import me.omigo.zoomanager.repositories.ZoneRepository;
import me.omigo.zoomanager.exceptions.ZoneNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AnimalFactory {

    private final ZoneRepository zoneRepository;

    public AnimalFactory(ZoneRepository zoneRepository) {
        this.zoneRepository = zoneRepository;
    }

    public Animal createAnimal(String species, String name, String zoneName) {
        int results = zoneRepository.findByName(zoneName).size();       //check if zone exists
        if (results == 0) {
            throw new ZoneNotFoundException(zoneName);                  //if not throw an exception
        } else {
            Zone zone = zoneRepository.findByName(zoneName).get(0);
            return switch (species) {
                case "lion" -> new Lion(name, zone);
                case "rabbit" -> new Rabbit(name, zone);
                case "elephant" -> new Elephant(name, zone);
                default -> throw new IllegalArgumentException("Invalid animal species: " + species);        //in case user passed invalid species
            };
        }
    }
}
