package pl.devcezz.cqrs.event;

public interface EventHandler<T extends Event> {
    void handle(T event);
}
