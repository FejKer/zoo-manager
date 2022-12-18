package me.omigo.zoomanager.controllers;

import me.omigo.zoomanager.entities.AnimalFactory;
import me.omigo.zoomanager.assemblers.AnimalModelAssembler;
import me.omigo.zoomanager.exceptions.AnimalNotFoundException;
import me.omigo.zoomanager.repositories.AnimalRepository;
import me.omigo.zoomanager.entities.Animal;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AnimalController {

    private final AnimalRepository animalRepository;
    private final AnimalModelAssembler animalModelAssembler;
    private final AnimalFactory animalFactory;

    public AnimalController(AnimalRepository animalRepository, AnimalModelAssembler animalModelAssembler, AnimalFactory animalFactory) {
        this.animalRepository = animalRepository;
        this.animalModelAssembler = animalModelAssembler;
        this.animalFactory = animalFactory;
    }

    @GetMapping("/animals")
    public CollectionModel<EntityModel<Animal>> getAllAnimals() {
        List<EntityModel<Animal>> animals = animalRepository.findAll().stream()
                .map(animalModelAssembler::toModel).collect(Collectors.toList());       //displays Animals with self links to make data RESTful

        return CollectionModel.of(animals, linkTo(methodOn(AnimalController.class).getAllAnimals()).withSelfRel());
    }

    @GetMapping("/animals/id/{id}")
    public EntityModel<Animal> getOneAnimal(@PathVariable Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new AnimalNotFoundException(id));

        return animalModelAssembler.toModel(animal);
    }

    @GetMapping("/animals/name/{name}")
    public CollectionModel<EntityModel<Animal>> getOneAnimal(@PathVariable String name) {
        List<EntityModel<Animal>> animals = animalRepository.findByName(name).stream()
                .map(animalModelAssembler::toModel).collect(Collectors.toList());

        if (animals.size() == 0) {
            throw new AnimalNotFoundException(name);    //if there are no results throw an exception
        }

        return CollectionModel.of(animals, linkTo(methodOn(AnimalController.class).getOneAnimal(name)).withRel("animals " + name));
    }

    @PostMapping("/animals")
    public Animal createAnimal(@RequestBody Map<String, String> request) {
        String species = request.get("species").toLowerCase();      //toLowerCase() just to avoid typos
        String name = request.get("name");
        String zone = request.get("zone");

        return animalRepository.save(animalFactory.createAnimal(species, name, zone));
    }

}
