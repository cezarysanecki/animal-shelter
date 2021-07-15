package pl.csanecki.animalshelter.___.animal;

import io.vavr.collection.Set;
import pl.csanecki.animalshelter.___.animal.vo.ShelterAnimal;
import pl.csanecki.animalshelter.___.animal.vo.ShelterLimits;
import pl.devcezz.cqrs.event.Event;

import static pl.csanecki.animalshelter.___.animal.AnimalEvent.AcceptingAnimalFailed.acceptingAnimalRejectedNow;
import static pl.csanecki.animalshelter.___.animal.AnimalEvent.AcceptingAnimalSucceeded.acceptingAnimalSucceededNow;
import static pl.csanecki.animalshelter.___.animal.AnimalEvent.AcceptingAnimalWarned.acceptingAnimalWarnedNow;

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
                return acceptingAnimalRejectedNow("Capacity of shelter is exceeded");
            }
            return acceptingAnimalWarnedNow("Safe threshold of shelter is reached or exceeded");
        }
        return acceptingAnimalSucceededNow();
    }

    private boolean capacityReachedAfterAccepting(Animal animal) {
        return shelterLimits.capacity() < shelterAnimals.length() + 1;
    }

    private boolean safeThresholdExceededAfterAccepting(Animal animal) {
        return shelterLimits.safeThreshold() <= shelterAnimals.length() + 1;
    }
}