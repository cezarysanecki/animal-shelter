package pl.devcezz.cqrs.exception;

import pl.devcezz.cqrs.command.Command;

public class NoHandlerForCommandException extends RuntimeException {

    public NoHandlerForCommandException(final Command command) {
        super("No command handler for " + command.getClass());
    }
}
