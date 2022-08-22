package pl.devcezz.shelter.catalogue;

import lombok.NonNull;
import lombok.Value;
import pl.devcezz.shelter.commons.events.DomainEvent;
import pl.devcezz.shelter.commons.model.Age;
import pl.devcezz.shelter.commons.model.Gender;
import pl.devcezz.shelter.commons.model.Name;
import pl.devcezz.shelter.commons.model.Species;

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
        @NonNull Name name;
        @NonNull Age age;
        @NonNull Species species;
        @NonNull Gender gender;

        public static AnimalConfirmedEvent animalConfirmedNow(
                AnimalId animalId, Name name, Age age, Species species, Gender gender) {
            return new AnimalConfirmedEvent(now(), animalId.getValue(), name, age, species, gender);
        }
    }
}
