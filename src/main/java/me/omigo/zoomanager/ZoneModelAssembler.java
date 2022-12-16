package me.omigo.zoomanager;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ZoneModelAssembler implements RepresentationModelAssembler<Zone, EntityModel<Zone>> {

    @Override
    public EntityModel<Zone> toModel(Zone zone) {
        EntityModel<Zone> model = EntityModel.of(zone,
                linkTo(methodOn(ZoneController.class).getOneZone(zone.getId())).withSelfRel(),
                linkTo(methodOn(ZoneController.class).getAllZones()).withRel("zones"));
        for (Animal a:
             zone.getAnimalSet()) {
             model.add(linkTo(methodOn(AnimalController.class).getOneAnimal(a.getId())).withSelfRel());
        }

        return model;
    }

    @Override
    public CollectionModel<EntityModel<Zone>> toCollectionModel(Iterable<? extends Zone> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
