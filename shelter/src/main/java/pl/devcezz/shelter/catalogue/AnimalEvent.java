package pl.devcezz.shelter.catalogue;

import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

import static java.time.Instant.now;

public interface AnimalEvent {

    @Value
    class AnimalCreatedEvent implements AnimalEvent {
        @NonNull Instant when;
        @NonNull UUID animalId;

        public static AnimalCreatedEvent animalCreatedNow(AnimalId animalId) {
            return new AnimalCreatedEvent(now(), animalId.getValue());
        }
    }

    @Value
    class AnimalDeletedEvent implements AnimalEvent {
        @NonNull Instant when;
        @NonNull UUID animalId;

        public static AnimalDeletedEvent animalDeletedNow(AnimalId animalId) {
            return new AnimalDeletedEvent(now(), animalId.getValue());
        }
    }
}
