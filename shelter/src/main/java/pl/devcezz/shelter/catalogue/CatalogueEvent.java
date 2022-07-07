package pl.devcezz.shelter.catalogue;

import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.commons.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;

public interface CatalogueEvent extends DomainEvent {

    default AnimalId animalId() {
        return AnimalId.of(getAnimalId());
    }

    UUID getAnimalId();

    @Value
    class AnimalConfirmedEvent implements CatalogueEvent {
        @NonNull UUID eventId = UUID.randomUUID();
        @NonNull Instant when;
        @NonNull UUID animalId;

        public static AnimalConfirmedEvent animalConfirmedNow(AnimalId animalId) {
            return new AnimalConfirmedEvent(now(), animalId.getValue());
        }
    }
}
