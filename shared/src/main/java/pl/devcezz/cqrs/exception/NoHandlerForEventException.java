package pl.devcezz.cqrs.exception;

import pl.devcezz.cqrs.event.Event;

public class NoHandlerForEventException extends RuntimeException {

    public NoHandlerForEventException(final Event event) {
        super("No event handler for " + event.getClass());
    }
}
