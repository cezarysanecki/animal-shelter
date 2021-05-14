package pl.devcezz.cqrs.command;

import pl.devcezz.cqrs.exception.NotImplementedCommandInterfaceException;
import pl.devcezz.cqrs.exception.NotImplementedHandleCommandInterfaceException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class AutoCommandsBus implements CommandsBus {

    private final Map<Type, HandleCommand> handlers;

    public AutoCommandsBus(final Set<HandleCommand> handlers) {
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(this::obtainHandledCommand, handler -> handler));
    }

    @Override
    public void send(final Command command) {
        var handleCommand = handlers.get(command.getClass());
        handleCommand.handle(command);
    }

    private Type obtainHandledCommand(final HandleCommand handler) {
        ParameterizedType handleCommandType = Arrays.stream(handler.getClass().getGenericInterfaces())
                .filter(type -> type instanceof ParameterizedType)
                .map(type -> (ParameterizedType) type)
                .filter(this::isHandleCommandInterfaceImplemented)
                .findFirst()
                .orElseThrow(NotImplementedHandleCommandInterfaceException::new);

        return Arrays.stream(handleCommandType.getActualTypeArguments())
                .map(this::acquireCommandImplementationType)
                .flatMap(Optional::stream)
                .findFirst()
                .orElseThrow(NotImplementedCommandInterfaceException::new);
    }

    private boolean isHandleCommandInterfaceImplemented(final ParameterizedType type) {
        return type.getRawType().equals(HandleCommand.class);
    }

    private Optional<Type> acquireCommandImplementationType(final Type argument) {
        return Optional.ofNullable(argument)
                .filter(not(type -> Command.class.equals(argument)))
                .filter(type -> Command.class.isAssignableFrom((Class<?>) type));
    }
}
