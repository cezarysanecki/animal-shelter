package pl.csanecki.animalshelter.___.animal;

import pl.devcezz.cqrs.event.Event;

import java.time.Instant;
import java.util.UUID;

public interface AnimalEvent extends Event {

    class AcceptingAnimalFailed implements AnimalEvent {
        private final UUID eventId = UUID.randomUUID();
        private final Instant when;
        private final String reason;

        private AcceptingAnimalFailed(final Instant when, final String reason) {
            this.when = when;
            this.reason = reason;
        }

        public static AcceptingAnimalFailed addingAnimalRejectedNow(String reason) {
            return new AcceptingAnimalFailed(Instant.now(), reason);
        }
    }

    class AcceptingAnimalWarned implements AnimalEvent {
        private final UUID eventId = UUID.randomUUID();
        private final Instant when;
        private final String message;

        private AcceptingAnimalWarned(final Instant when, final String message) {
            this.when = when;
            this.message = message;
        }

        public static AcceptingAnimalWarned addingAnimalWarnedNow(String message) {
            return new AcceptingAnimalWarned(Instant.now(), message);
        }
    }

    class AcceptingAnimalSucceeded implements AnimalEvent {
        private final UUID eventId = UUID.randomUUID();
        private final Instant when;

        private AcceptingAnimalSucceeded(final Instant when) {
            this.when = when;
        }

        public static AcceptingAnimalSucceeded addingAnimalSucceededNow() {
            return new AcceptingAnimalSucceeded(Instant.now());
        }
    }
}
