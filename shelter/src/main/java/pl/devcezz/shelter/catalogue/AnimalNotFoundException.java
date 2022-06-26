package pl.devcezz.shelter.catalogue;

import java.util.UUID;

public class AnimalNotFoundException extends RuntimeException {

    public AnimalNotFoundException(UUID animalId) {
        super("animal not found: " + animalId);
    }
}
