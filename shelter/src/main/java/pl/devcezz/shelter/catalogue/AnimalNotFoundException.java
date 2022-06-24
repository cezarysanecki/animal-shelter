package pl.devcezz.shelter.catalogue;

import pl.devcezz.shelter.ShelterException;

import java.util.UUID;

public class AnimalNotFoundException extends ShelterException {

    public AnimalNotFoundException(UUID animalId) {
        super("animal not found: " + animalId);
    }
}
