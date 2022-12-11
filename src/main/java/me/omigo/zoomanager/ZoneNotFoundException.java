package me.omigo.zoomanager;

public class ZoneNotFoundException extends RuntimeException{
    ZoneNotFoundException(Long id) {
        super("Could not find zone " + id);
    }
}
