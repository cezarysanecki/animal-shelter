package pl.devcezz.animalshelter.shelter.application.exception;

public class ShelterLimitExceededException extends AnimalShelterException {

    public ShelterLimitExceededException(final String message) {
        super(message);
    }
}
