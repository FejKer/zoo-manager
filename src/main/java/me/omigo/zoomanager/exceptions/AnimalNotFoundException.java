package me.omigo.zoomanager.exceptions;

public class AnimalNotFoundException extends RuntimeException {
  public AnimalNotFoundException(Long id) {
    super("Could not find animal " + id);
  }

  public AnimalNotFoundException(String name) {
    super("Could not find animal " + name);
  }
}
