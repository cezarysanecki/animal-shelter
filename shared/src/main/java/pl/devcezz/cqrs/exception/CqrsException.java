package pl.devcezz.cqrs.exception;

public class CqrsException extends RuntimeException {

    public CqrsException() { }

    public CqrsException(final String message) {
        super(message);
    }
}
