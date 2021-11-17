package pl.devcezz.animalshelter.shelter.exception;

import pl.devcezz.animalshelter.commons.exception.AnimalShelterException;

public class ShelterLimitExceededException extends AnimalShelterException {

    public ShelterLimitExceededException(final String message) {
        super(message);
    }
}
