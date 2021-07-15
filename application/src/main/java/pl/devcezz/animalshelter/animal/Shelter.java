package pl.devcezz.animalshelter.animal;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.animal.vo.ShelterAnimal;
import pl.devcezz.animalshelter.animal.vo.ShelterLimits;
import pl.devcezz.cqrs.event.Event;

class Shelter {

    private final ShelterLimits shelterLimits;
    private final Set<ShelterAnimal> shelterAnimals;

    Shelter(
            final ShelterLimits shelterLimits,
            final Set<ShelterAnimal> shelterAnimals
    ) {
        this.shelterLimits = shelterLimits;
        this.shelterAnimals = shelterAnimals;
    }

    Event accept(Animal animal) {
        if (safeThresholdExceededAfterAccepting(animal)) {
            if (capacityReachedAfterAccepting(animal)) {
                return AnimalEvent.AcceptingAnimalFailed.acceptingAnimalRejectedNow("Capacity of shelter is exceeded");
            }
            return AnimalEvent.AcceptingAnimalWarned.acceptingAnimalWarnedNow("Safe threshold of shelter is reached or exceeded");
        }
        return AnimalEvent.AcceptingAnimalSucceeded.acceptingAnimalSucceededNow();
    }

    private boolean capacityReachedAfterAccepting(Animal animal) {
        return shelterLimits.capacity() < shelterAnimals.length() + 1;
    }

    private boolean safeThresholdExceededAfterAccepting(Animal animal) {
        return shelterLimits.safeThreshold() <= shelterAnimals.length() + 1;
    }
}