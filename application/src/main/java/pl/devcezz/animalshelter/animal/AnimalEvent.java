package pl.devcezz.animalshelter.animal;

import pl.devcezz.cqrs.event.Event;

public interface AnimalEvent extends Event {

    class AcceptingAnimalFailed implements AnimalEvent {
        private final String reason;

        private AcceptingAnimalFailed(final String reason) {
            this.reason = reason;
        }

        public static AcceptingAnimalFailed acceptingAnimalRejected(final String reason) {
            return new AcceptingAnimalFailed(reason);
        }

        public String getReason() {
            return reason;
        }
    }

    class AcceptingAnimalWarned implements AnimalEvent {

        private AcceptingAnimalWarned() {}

        public static AcceptingAnimalWarned acceptingAnimalWarned() {
            return new AcceptingAnimalWarned();
        }
    }

    class AcceptingAnimalSucceeded implements AnimalEvent {

        private AcceptingAnimalSucceeded() {}

        public static AcceptingAnimalSucceeded acceptingAnimalSucceeded() {
            return new AcceptingAnimalSucceeded();
        }
    }

    class AdoptedAnimalSucceeded implements AnimalEvent {
        private final AnimalId animalId;

        private AdoptedAnimalSucceeded(final AnimalId animalId) {
            this.animalId = animalId;
        }

        public static AdoptedAnimalSucceeded adoptingAnimalSucceeded(final AnimalId animalId) {
            return new AdoptedAnimalSucceeded(animalId);
        }

        public AnimalId getAnimalId() {
            return animalId;
        }
    }
}
