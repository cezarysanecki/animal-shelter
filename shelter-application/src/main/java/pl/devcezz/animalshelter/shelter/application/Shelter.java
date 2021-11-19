package pl.devcezz.animalshelter.shelter.application;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent;
import pl.devcezz.cqrs.event.Event;

class Shelter {

    private final ShelterLimits shelterLimits;
    private final Set<ShelterAnimal.AvailableAnimal> shelterAnimals;

    Shelter(
            final ShelterLimits shelterLimits,
            final Set<ShelterAnimal.AvailableAnimal> shelterAnimals
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

    private AnimalEvent.FailedAnimalAcceptance acceptingAnimalFailed() {
        return new AnimalEvent.FailedAnimalAcceptance(AnimalEvent.FailedAnimalAcceptance.Reason.NotEnoughSpace.value);
    }

    private AnimalEvent.WarnedAnimalAcceptance acceptingAnimalWarned(final Animal animal) {
        return new AnimalEvent.WarnedAnimalAcceptance(
                animal.getId().value(),
                animal.getName().value(),
                animal.getAge().value(),
                animal.getSpecies().name(),
                AnimalEvent.WarnedAnimalAcceptance.Message.SafeThresholdIsReached.value
        );
    }

    private AnimalEvent.SuccessfulAnimalAcceptance acceptingAnimalSucceeded(final Animal animal) {
        return new AnimalEvent.SuccessfulAnimalAcceptance(
                animal.getId().value(),
                animal.getName().value(),
                animal.getAge().value(),
                animal.getSpecies().name()
        );
    }
}