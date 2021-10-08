package pl.devcezz.cqrs.event;

public interface EventsBus {
    void publish(Event event);
}
