package me.omigo.zoomanager.exceptions;

public class ZoneNotFoundException extends RuntimeException{
    public ZoneNotFoundException(Long id) {
        super("Could not find zone " + id);
    }

    public ZoneNotFoundException(String name) {
        super("Could not find zone " + name);
    }
}
