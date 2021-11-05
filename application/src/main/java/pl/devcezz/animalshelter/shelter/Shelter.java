package pl.devcezz.animalshelter.shelter;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AvailableAnimal;
import pl.devcezz.cqrs.event.Event;

import static pl.devcezz.animalshelter.shelter.event.AnimalEvent.AcceptingAnimalFailed;
import static pl.devcezz.animalshelter.shelter.event.AnimalEvent.AcceptingAnimalSucceeded.AcceptingAnimalSucceeded;
import static pl.devcezz.animalshelter.shelter.event.AnimalEvent.AcceptingAnimalWarned;

class Shelter {

    private final ShelterLimits shelterLimits;
    private final Set<AvailableAnimal> shelterAnimals;

    Shelter(
            final ShelterLimits shelterLimits,
            final Set<AvailableAnimal> shelterAnimals
    ) {
        this.shelterLimits = shelterLimits;
        this.shelterAnimals = shelterAnimals;
    }

    Event accept(Animal animal) {
        if (safeThresholdExceededAfterAccepting(animal)) {
            if (capacityReachedAfterAccepting(animal)) {
                return acceptingAnimalFailed();
            }
            return acceptingAnimalWarned(animal);
        }
        return acceptingAnimalSucceeded(animal);
    }

    private boolean capacityReachedAfterAccepting(Animal animal) {
        return shelterLimits.capacity() < shelterAnimals.length() + 1;
    }

    private boolean safeThresholdExceededAfterAccepting(Animal animal) {
        return shelterLimits.safeThreshold() <= shelterAnimals.length() + 1;
    }

    private AcceptingAnimalFailed acceptingAnimalFailed() {
        return new AcceptingAnimalFailed("not enough space in shelter");
    }

    private AcceptingAnimalWarned acceptingAnimalWarned(final Animal animal) {
        return new AcceptingAnimalWarned(
                animal.getId().value(),
                animal.getName().value(),
                animal.getAge().value(),
                animal.getSpecies().name()
        );
    }

    private AcceptingAnimalSucceeded acceptingAnimalSucceeded(final Animal animal) {
        return new AcceptingAnimalSucceeded(
                animal.getId().value(),
                animal.getName().value(),
                animal.getAge().value(),
                animal.getSpecies().name()
        );
    }
}