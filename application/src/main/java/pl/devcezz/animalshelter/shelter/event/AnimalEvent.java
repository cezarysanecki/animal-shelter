package pl.devcezz.animalshelter.shelter.event;

import pl.devcezz.cqrs.event.Event;

import java.util.UUID;

public interface AnimalEvent extends Event {

    record AcceptingAnimalFailed(String reason) implements AnimalEvent {}

    record AcceptingAnimalWarned(UUID animalId, String animalName,
                                 Integer animalAge, String animalSpecies) implements AnimalEvent {}

    record AcceptingAnimalSucceeded(UUID animalId, String animalName,
                                    Integer animalAge, String animalSpecies) implements AnimalEvent {}

    record AnimalAdoptionSucceeded(UUID animalId, String animalName,
                                   Integer animalAge, String animalSpecies) implements AnimalEvent {}
}
