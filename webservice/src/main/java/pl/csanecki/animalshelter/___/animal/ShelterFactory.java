package pl.csanecki.animalshelter.___.animal;

import io.vavr.collection.Set;
import pl.csanecki.animalshelter.___.animal.vo.ShelterAnimal;
import pl.csanecki.animalshelter.___.animal.vo.ShelterLimits;

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
