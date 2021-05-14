package pl.devcezz.cqrs.command;

import pl.devcezz.cqrs.exception.NotImplementedCommandInterfaceException;
import pl.devcezz.cqrs.exception.NotImplementedHandleCommandInterfaceException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class AutoCommandsBus implements CommandsBus {

    private final Map<Type, HandleCommand> handlers = new HashMap<>();

    public AutoCommandsBus(final Set<HandleCommand> handlers) {
        handlers.forEach(handler -> {
            ParameterizedType handleCommandType = Arrays.stream(handler.getClass().getGenericInterfaces())
                    .filter(this::isParameterizedTypeClass)
                    .map(this::castToParameterizedTypeClass)
                    .filter(this::isHandleCommandInterfaceImplemented)
                    .findFirst()
                    .orElseThrow(NotImplementedHandleCommandInterfaceException::new);

            Type commandType = Arrays.stream(handleCommandType.getActualTypeArguments())
                    .map(this::acquireCommandImplementation)
                    .findFirst()
                    .orElseThrow(NotImplementedCommandInterfaceException::new);

            this.handlers.put(commandType, handler);
        });
    }

    @Override
    public void send(final Command command) {
        var handleCommand = handlers.get(command.getClass());
        handleCommand.handle(command);
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

    private Type acquireCommandImplementation(final Type type) {
        return Optional.ofNullable(type)
                .filter(this::isCommandInterfaceImplemented)
                .orElseThrow(NotImplementedCommandInterfaceException::new);
    }

    private boolean isCommandInterfaceImplemented(final Type type) {
        return Command.class.isAssignableFrom((Class<?>) type);
    }
}
