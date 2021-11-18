package pl.devcezz.animalshelter.shelter.application.exception;

public abstract class AnimalShelterException extends RuntimeException {

    public AnimalShelterException() {}

    public AnimalShelterException(final String message) {
        super(message);
    }
}
