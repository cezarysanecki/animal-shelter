package pl.devcezz.cqrs.event;

import pl.devcezz.cqrs.exception.NotImplementedEventInterfaceException;
import pl.devcezz.cqrs.exception.NotImplementedHandleEventInterfaceException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.function.Predicate.not;

public class AutoEventBus implements EventsBus {

    private final Map<Type, Set<HandleEvent>> handlers;

    public AutoEventBus(final Set<HandleEvent> handlers) {
        this.handlers = handlers.stream()
                .collect(Collectors.groupingBy(this::obtainHandledEvent, Collectors.toSet()));
    }

    private Type obtainHandledEvent(final HandleEvent handler) {
        ParameterizedType handleEventType = Arrays.stream(handler.getClass().getGenericInterfaces())
                .filter(type -> type instanceof ParameterizedType)
                .map(type -> (ParameterizedType) type)
                .filter(this::isHandleEventInterfaceImplemented)
                .findFirst()
                .orElseThrow(NotImplementedHandleEventInterfaceException::new);

        return Arrays.stream(handleEventType.getActualTypeArguments())
                .map(this::acquireEventImplementationType)
                .findFirst()
                .orElseThrow(NotImplementedEventInterfaceException::new);
    }

    private boolean isHandleEventInterfaceImplemented(final ParameterizedType type) {
        return type.getRawType().equals(HandleEvent.class);
    }

    private Type acquireEventImplementationType(final Type argument) {
        return Optional.ofNullable(argument)
                .filter(not(type -> Event.class.equals(argument)))
                .filter(type -> Event.class.isAssignableFrom((Class<?>) type))
                .orElseThrow(NotImplementedEventInterfaceException::new);
    }

    @Override
    public void publish(final Event event) {
        handlers.get(event)
                .forEach(handler -> handler.handle(event));
    }
}
