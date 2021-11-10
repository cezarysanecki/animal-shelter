package pl.devcezz.animalshelter.shelter.event;

import pl.devcezz.cqrs.event.Event;

import java.util.UUID;

public interface AnimalEvent extends Event {

    record FailedAnimalAcceptance(String reason) implements AnimalEvent {
        public enum Reason {
            NotEnoughSpace("not enough space in shelter");

            public final String value;

            Reason(final String value) {
                this.value = value;
            }
        }
    }

    record WarnedAnimalAcceptance(UUID animalId, String animalName,
                                  Integer animalAge, String animalSpecies,
                                  String message) implements AnimalEvent {
        public enum Message {
            SafeThresholdIsReached("safe threshold is reached or exceeded");

            public final String value;

            Message(final String value) {
                this.value = value;
            }
        }
    }

    record SuccessfulAnimalAcceptance(UUID animalId, String animalName,
                                      Integer animalAge, String animalSpecies) implements AnimalEvent {}

    record SuccessfulAnimalAdoption(UUID animalId, String animalName,
                                    Integer animalAge, String animalSpecies) implements AnimalEvent {}
}
