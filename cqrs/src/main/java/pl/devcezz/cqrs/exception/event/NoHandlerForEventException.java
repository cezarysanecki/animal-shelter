package pl.devcezz.cqrs.exception.event;

import pl.devcezz.cqrs.event.Event;
import pl.devcezz.cqrs.exception.CqrsException;

public class NoHandlerForEventException extends CqrsException {

    public NoHandlerForEventException(final Event event) {
        super("No event handler for " + event.getClass());
    }
}
