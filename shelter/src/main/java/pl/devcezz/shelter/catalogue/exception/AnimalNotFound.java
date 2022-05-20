package pl.devcezz.shelter.catalogue.exception;

import pl.devcezz.shelter.ShelterException;
import pl.devcezz.shelter.catalogue.AnimalId;

public class AnimalNotFound extends ShelterException {

    public AnimalNotFound(AnimalId animalId) {
        super("animal not found: " + animalId.getValue());
    }
}
