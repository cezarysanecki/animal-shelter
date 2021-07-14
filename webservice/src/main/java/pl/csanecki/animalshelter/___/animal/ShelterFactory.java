package pl.csanecki.animalshelter.___.animal;

import io.vavr.collection.Set;
import pl.csanecki.animalshelter.___.animal.vo.ShelterLimits;
import pl.csanecki.animalshelter.___.species.Species;
import pl.csanecki.animalshelter.___.species.SpeciesRepository;

import static pl.csanecki.animalshelter.___.animal.AcceptingAnimalPolicy.allCurrentPolicies;

class ShelterFactory {

    private final ShelterRepository shelterRepository;
    private final SpeciesRepository speciesRepository;

    ShelterFactory(final ShelterRepository shelterRepository, final SpeciesRepository speciesRepository) {
        this.shelterRepository = shelterRepository;
        this.speciesRepository = speciesRepository;
    }

    public Shelter create() {
        ShelterLimits shelterLimits = shelterRepository.queryForShelterLimits();
        Set<ShelterAnimal> shelterAnimals = shelterRepository.queryForAnimalsInShelter();
        Set<Species> acceptableSpecies = speciesRepository.findAllSpecies();

        return new Shelter(
                allCurrentPolicies(),
                shelterLimits,
                shelterAnimals,
                acceptableSpecies
        );
    }
}
