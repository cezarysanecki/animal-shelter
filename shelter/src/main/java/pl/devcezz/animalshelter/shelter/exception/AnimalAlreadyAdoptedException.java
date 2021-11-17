package pl.devcezz.animalshelter.shelter.exception;

import pl.devcezz.animalshelter.commons.exception.AnimalShelterException;

public class AnimalAlreadyAdoptedException extends AnimalShelterException {

    public AnimalAlreadyAdoptedException(final String message) {
        super(message);
    }
}
