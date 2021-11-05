package pl.devcezz.cqrs.exception.command;

import pl.devcezz.cqrs.command.Command;
import pl.devcezz.cqrs.exception.CqrsException;

public class NoHandlerForCommandException extends CqrsException {

    public NoHandlerForCommandException(final Command command) {
        super("No command handler for " + command.getClass());
    }
}
