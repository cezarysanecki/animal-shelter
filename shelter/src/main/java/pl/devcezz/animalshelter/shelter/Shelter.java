package pl.devcezz.animalshelter.shelter;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AvailableAnimal;
import pl.devcezz.cqrs.event.Event;

import static pl.devcezz.animalshelter.shelter.event.AnimalEvent.FailedAnimalAcceptance;
import static pl.devcezz.animalshelter.shelter.event.AnimalEvent.SuccessfulAnimalAcceptance;
import static pl.devcezz.animalshelter.shelter.event.AnimalEvent.WarnedAnimalAcceptance;

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

    private FailedAnimalAcceptance acceptingAnimalFailed() {
        return new FailedAnimalAcceptance(FailedAnimalAcceptance.Reason.NotEnoughSpace.value);
    }

    private WarnedAnimalAcceptance acceptingAnimalWarned(final Animal animal) {
        return new WarnedAnimalAcceptance(
                animal.getId().value(),
                animal.getName().value(),
                animal.getAge().value(),
                animal.getSpecies().name(),
                WarnedAnimalAcceptance.Message.SafeThresholdIsReached.value
        );
    }

    private SuccessfulAnimalAcceptance acceptingAnimalSucceeded(final Animal animal) {
        return new SuccessfulAnimalAcceptance(
                animal.getId().value(),
                animal.getName().value(),
                animal.getAge().value(),
                animal.getSpecies().name()
        );
    }
}