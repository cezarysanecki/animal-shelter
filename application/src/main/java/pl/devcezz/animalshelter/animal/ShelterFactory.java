package pl.devcezz.animalshelter.animal;

import io.vavr.collection.Set;

class ShelterFactory {

    private final ShelterRepository shelterRepository;

    ShelterFactory(final ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public Shelter create() {
        ShelterLimits shelterLimits = shelterRepository.queryForShelterLimits();
        Set<ShelterAnimal> shelterAnimals = shelterRepository.queryForAnimalsInShelter();

        return new Shelter(
                shelterLimits,
                shelterAnimals
        );
    }
}
