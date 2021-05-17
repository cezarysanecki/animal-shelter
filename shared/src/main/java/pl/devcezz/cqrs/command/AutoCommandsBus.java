package pl.devcezz.cqrs.command;

import pl.devcezz.cqrs.exception.command.NoHandlerForCommandException;
import pl.devcezz.cqrs.exception.command.NotImplementedCommandInterfaceException;
import pl.devcezz.cqrs.exception.command.NotImplementedCommandHandlerInterfaceException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class AutoCommandsBus implements CommandsBus {

    private final Map<Type, CommandHandler> handlers;

    public AutoCommandsBus(final Set<CommandHandler> handlers) {
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(this::obtainHandledCommand, handler -> handler));
    }

    @Override
    public void send(final Command command) {
        Optional.ofNullable(handlers.get(command.getClass()))
                .ifPresentOrElse(handler -> handler.handle(command), () -> { throw new NoHandlerForCommandException(command); });
    }

    private Type obtainHandledCommand(final CommandHandler handler) {
        ParameterizedType commandHandlerType = Arrays.stream(handler.getClass().getGenericInterfaces())
                .filter(type -> type instanceof ParameterizedType)
                .map(type -> (ParameterizedType) type)
                .filter(this::isCommandHandlerInterfaceImplemented)
                .findFirst()
                .orElseThrow(NotImplementedCommandHandlerInterfaceException::new);

        return Arrays.stream(commandHandlerType.getActualTypeArguments())
                .map(this::acquireCommandImplementationType)
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(NotImplementedCommandInterfaceException::new);
    }

    private boolean isCommandHandlerInterfaceImplemented(final ParameterizedType type) {
        return type.getRawType().equals(CommandHandler.class);
    }

    private Optional<Type> acquireCommandImplementationType(final Type argument) {
        return Optional.ofNullable(argument)
                .filter(not(type -> Command.class.equals(argument)))
                .filter(type -> Command.class.isAssignableFrom((Class<?>) type));
    }

    Map<Type, CommandHandler> getHandlers() {
        return new HashMap<>(handlers);
    }
}
