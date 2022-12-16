package me.omigo.zoomanager;

public class AnimalNotFoundException extends RuntimeException {
    AnimalNotFoundException(Long id) {
        super("Could not find animal " + id);
    }
    AnimalNotFoundException(String name) {
        super("Could not find animal " + name);
    }
}
