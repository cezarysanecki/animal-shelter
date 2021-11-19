package pl.devcezz.animalshelter.shelter.application.exception;

public class AnimalAlreadyAdoptedException extends AnimalShelterException {

    public AnimalAlreadyAdoptedException(final String message) {
        super(message);
    }
}
