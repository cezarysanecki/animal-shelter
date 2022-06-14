package pl.devcezz.shelter.catalogue.exception;

import pl.devcezz.shelter.ShelterException;

import java.util.UUID;

public class AnimalNotFound extends ShelterException {

    public AnimalNotFound(UUID animalId) {
        super("animal not found: " + animalId);
    }
}
