package pl.devcezz.animalshelter.commons.exception;

public class ShelterLimitExceededException extends AnimalShelterException {

    public ShelterLimitExceededException(final String message) {
        super(message);
    }
}
