package me.omigo.zoomanager.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;
import me.omigo.zoomanager.assemblers.ZoneModelAssembler;
import me.omigo.zoomanager.entities.Animal;
import me.omigo.zoomanager.entities.Zone;
import me.omigo.zoomanager.exceptions.ZoneNotFoundException;
import me.omigo.zoomanager.repositories.ZoneRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

@RestController
public class ZoneController {
  private final ZoneRepository zoneRepository;
  private final ZoneModelAssembler zoneModelAssembler;

  public ZoneController(ZoneRepository zoneRepository, ZoneModelAssembler zoneModelAssembler) {
    this.zoneRepository = zoneRepository;
    this.zoneModelAssembler = zoneModelAssembler;
  }

  @GetMapping("/zones")
  public CollectionModel<EntityModel<Zone>> getAllZones() {
    List<EntityModel<Zone>> zones =
        zoneRepository
            .findAll()
            .stream() // putting zone through ZoneModelAssembler to make data RESTful
            .map(zoneModelAssembler::toModel)
            .collect(Collectors.toList());

    return CollectionModel.of(
        zones, linkTo(methodOn(ZoneController.class).getAllZones()).withSelfRel());
  }

  @GetMapping("/zones/id/{id}")
  public EntityModel<Zone> getOneZone(@PathVariable Long id) {
    Zone zone = zoneRepository.findById(id).orElseThrow(() -> new ZoneNotFoundException(id));

    return zoneModelAssembler.toModel(zone);
  }

  @GetMapping("/zones/name/{name}")
  public EntityModel<Zone> getOneZone(@PathVariable String name) {
    if (zoneRepository.findByName(name).size() == 0) {
      throw new ZoneNotFoundException(name);
    }
    Zone zone = zoneRepository.findByName(name).get(0);

    return zoneModelAssembler.toModel(zone);
  }

  @GetMapping("/zones/most-food")
  public EntityModel<Zone> getZoneMostFood() {
    if (zoneRepository.findAll().size() == 0) {
      throw new IllegalArgumentException(
          "No zones to display"); // in case there are no zones in database
    }
    Zone zone = null;
    int maxFood = 0; // initial values
    int zoneFood = 0;
    for (Zone z : zoneRepository.findAll()) { // loop through zones
      for (Animal a : z.getAnimalSet()) { // and count required food
        zoneFood += a.getRequiredFood();
      }
      if (zoneFood > maxFood) {
        maxFood = zoneFood;
        zone = z;
      }
      zoneFood = 0;
    }
    assert zone != null;
    return zoneModelAssembler.toModel(zone);
  }

  @GetMapping("/zones/least-populated")
  public EntityModel<Zone> getZoneLeastPopulated() {
    if (zoneRepository.findAll().size() == 0) {
      throw new IllegalArgumentException(
          "No zones to display"); // in case there are no zones in database
    }
    Zone zone = null;
    int leastAnimals = Integer.MAX_VALUE; // initial value
    for (Zone z : zoneRepository.findAll()) { // loop through zones
      if (z.getAnimalSet().size() < leastAnimals) {
        leastAnimals = z.getAnimalSet().size();
        zone = z;
      }
    }
    assert zone != null;
    return zoneModelAssembler.toModel(zone);
  }

  @PostMapping("/zones")
  public Zone createZone(@RequestBody Zone zone) {
    if (zoneRepository.findByName(zone.getName()).size() != 0) {
      throw new IllegalArgumentException(
          "Zone exists with name " + zone.getName()); // making the name unique on runtime level
    } else {
      return zoneRepository.save(zone);
    }
  }
}
