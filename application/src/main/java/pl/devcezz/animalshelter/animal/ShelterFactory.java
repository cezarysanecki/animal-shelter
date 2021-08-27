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
        Set<ShelterAnimal> shelterAnimals = shelterRepository.queryForAnimalsInShelter();

        if (shelterLimits.capacity() < shelterAnimals.length()) {
            throw new ShelterLimitExceededException("more animals in shelter than capacity");
        }

        return new Shelter(
                shelterLimits,
                shelterAnimals
        );
    }
}
