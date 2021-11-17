package pl.devcezz.animalshelter.shelter.exception;

import pl.devcezz.animalshelter.commons.exception.AnimalShelterException;

public class AcceptingAnimalRejectedException extends AnimalShelterException {

    public AcceptingAnimalRejectedException(final String message) {
        super(message);
    }
}
