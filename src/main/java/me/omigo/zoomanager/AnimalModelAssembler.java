package me.omigo.zoomanager;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AnimalModelAssembler implements RepresentationModelAssembler<Animal, EntityModel<Animal>> {
    @Override
    public EntityModel<Animal> toModel(Animal animal) {
        return EntityModel.of(animal,
                linkTo(methodOn(AnimalController.class).getOneAnimal(animal.getId())).withSelfRel(),
                linkTo(methodOn(AnimalController.class).getAllAnimals()).withRel("employees"));
    }

    @Override
    public CollectionModel<EntityModel<Animal>> toCollectionModel(Iterable<? extends Animal> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
