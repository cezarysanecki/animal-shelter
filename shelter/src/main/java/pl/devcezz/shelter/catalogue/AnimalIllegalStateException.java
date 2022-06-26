package pl.devcezz.shelter.catalogue;

import java.util.UUID;

public class AnimalIllegalStateException extends RuntimeException {

    private AnimalIllegalStateException(String action, UUID animalId) {
        super("cannot " + action + " animal data with id: " + animalId);
    }

    public static AnimalIllegalStateException exceptionCannotUpdate(UUID animalId) {
        return new AnimalIllegalStateException("update", animalId);
    }

    public static AnimalIllegalStateException exceptionCannotDelete(UUID animalId) {
        return new AnimalIllegalStateException("delete", animalId);
    }
}
