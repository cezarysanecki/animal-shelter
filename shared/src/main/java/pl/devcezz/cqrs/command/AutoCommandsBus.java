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
                .filter(this::isParameterizedTypeClass)
                .map(this::castToParameterizedTypeClass)
                .filter(this::isHandleCommandInterfaceImplemented)
                .findFirst()
                .orElseThrow(NotImplementedHandleCommandInterfaceException::new);

        return Arrays.stream(handleCommandType.getActualTypeArguments())
                .map(this::acquireCommandImplementationType)
                .findFirst()
                .orElseThrow(NotImplementedCommandInterfaceException::new);
    }

    private boolean isParameterizedTypeClass(final Type type) {
        return type instanceof ParameterizedType;
    }

    private ParameterizedType castToParameterizedTypeClass(final Type genericInterface) {
        return (ParameterizedType) genericInterface;
    }

    private boolean isHandleCommandInterfaceImplemented(final ParameterizedType type) {
        return type.getRawType().equals(HandleCommand.class);
    }

    private Type acquireCommandImplementationType(final Type type) {
        return Optional.ofNullable(type)
                .filter(this::isCommandInterfaceImplemented)
                .orElseThrow(NotImplementedCommandInterfaceException::new);
    }

    private boolean isCommandInterfaceImplemented(final Type type) {
        return Command.class.isAssignableFrom((Class<?>) type);
    }
}
