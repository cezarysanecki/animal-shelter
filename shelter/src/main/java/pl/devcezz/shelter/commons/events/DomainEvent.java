package pl.devcezz.shelter.commons.events;

import io.vavr.collection.List;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {

    UUID getEventId();

    Instant getWhen();

    default List<DomainEvent> normalize() {
        return List.of(this);
    }
}
