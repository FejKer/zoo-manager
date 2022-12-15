package me.omigo.zoomanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    @GetMapping("/animals/{id}")
    public EntityModel<Animal> one(@PathVariable Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(() -> new AnimalNotFoundException(id));

        return animalModelAssembler.toModel(animal);
    }

    @PostMapping("/animals")
    public Animal createAnimal(@RequestBody Animal animal) {
        return animalRepository.save(animal);
    }

    // other endpoints
}
