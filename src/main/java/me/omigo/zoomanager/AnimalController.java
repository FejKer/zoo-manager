package me.omigo.zoomanager;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class AnimalController {

    private final AnimalRepository animalRepository;
    private final AnimalModelAssembler animalModelAssembler;

    public AnimalController(AnimalRepository animalRepository, AnimalModelAssembler animalModelAssembler) {
        this.animalRepository = animalRepository;
        this.animalModelAssembler = animalModelAssembler;
    }

    @GetMapping("/animals")
    public CollectionModel<EntityModel<Animal>> getAllAnimals() {
        List<EntityModel<Animal>> animals = animalRepository.findAll().stream() //
                .map(animalModelAssembler::toModel) //
                .collect(Collectors.toList());

        return CollectionModel.of(animals, linkTo(methodOn(AnimalController.class).getAllAnimals()).withSelfRel());
    }

    @GetMapping("/animals/{id}")
    public EntityModel<Animal> getOneAnimal(@PathVariable Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new AnimalNotFoundException(id));

        return animalModelAssembler.toModel(animal);
    }

    @GetMapping("/animals/name/{name}")
    public CollectionModel<EntityModel<Animal>> getOneAnimal(@PathVariable String name) {
        List<EntityModel<Animal>> animals = new ArrayList<>();
        for (Animal a:
             animalRepository.findAll()) {
            if (a.getName().equals(name)) {
                animals.add(animalModelAssembler.toModel(a));
            }
        }
        if (animals.size() == 0) throw new AnimalNotFoundException(name);

        return CollectionModel.of(animals, linkTo(methodOn(AnimalController.class).getOneAnimal(name)).withRel("animals " + name));
    }

    @PostMapping("/animals")
    public Animal createAnimal(@RequestBody Animal animal) {
        return animalRepository.save(animal);
    }

    // other endpoints
}
