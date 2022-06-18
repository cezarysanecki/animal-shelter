package pl.devcezz.shelter.catalogue.exception;

import pl.devcezz.shelter.ShelterException;

import java.util.UUID;

public class AnimalIllegalStateException extends ShelterException {

    private AnimalIllegalStateException(String action, UUID animalId) {
        super("cannot " + action + " animal data: " + animalId);
    }

    public static AnimalIllegalStateException exceptionCannotUpdate(UUID animalId) {
        return new AnimalIllegalStateException("update", animalId);
    }

    public static AnimalIllegalStateException exceptionCannotDelete(UUID animalId) {
        return new AnimalIllegalStateException("delete", animalId);
    }
}
