package pl.devcezz.animalshelter.mail.model;

public class ContextCreationFailedException extends RuntimeException {

    public ContextCreationFailedException(final String message) {
        super(message);
    }
}
