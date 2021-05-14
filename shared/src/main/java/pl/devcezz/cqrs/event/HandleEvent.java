package pl.devcezz.cqrs.event;

public interface HandleEvent<T extends Event> {
    void handle(T event);
}
