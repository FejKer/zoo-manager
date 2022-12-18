package me.omigo.zoomanager.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import me.omigo.zoomanager.controllers.AnimalController;
import me.omigo.zoomanager.controllers.ZoneController;
import me.omigo.zoomanager.entities.Animal;
import me.omigo.zoomanager.entities.Zone;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ZoneModelAssembler implements RepresentationModelAssembler<Zone, EntityModel<Zone>> {

  @Override
  public EntityModel<Zone> toModel(Zone zone) {
    EntityModel<Zone> model =
        EntityModel.of(
            zone, // return model of Zone
            linkTo(methodOn(ZoneController.class).getOneZone(zone.getId()))
                .withSelfRel(), // append link to itself
            linkTo(methodOn(ZoneController.class).getAllZones())
                .withRel("zones")); // link to all zones
    for (Animal a : zone.getAnimalSet()) {
      model.add(
          linkTo(methodOn(AnimalController.class).getOneAnimal(a.getId()))
              .withRel(
                  "animals")); // and to animals this zone has (as we used @JsonIgnore on animalSet)
    }

    return model;
  }

  @Override
  public CollectionModel<EntityModel<Zone>> toCollectionModel(Iterable<? extends Zone> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
