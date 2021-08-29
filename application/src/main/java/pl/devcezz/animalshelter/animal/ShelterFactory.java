package pl.devcezz.animalshelter.animal;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.commons.exception.ShelterLimitExceededException;

class ShelterFactory {

    private final ShelterRepository shelterRepository;

    ShelterFactory(final ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public Shelter create() {
        ShelterLimits shelterLimits = shelterRepository.queryForShelterLimits();
        Set<AvailableAnimal> availableAnimals = shelterRepository.queryForAvailableAnimals();

        if (shelterLimits.capacity() < availableAnimals.length()) {
            throw new ShelterLimitExceededException("more animals in shelter than capacity");
        }

        return new Shelter(
                shelterLimits,
                availableAnimals
        );
    }
}
