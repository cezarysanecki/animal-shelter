package pl.csanecki.animalshelter.___.animal;

import io.vavr.collection.Set;
import pl.csanecki.animalshelter.___.animal.vo.ShelterLimits;
import pl.csanecki.animalshelter.___.species.Species;
import pl.devcezz.cqrs.event.Event;

import static pl.csanecki.animalshelter.___.animal.AnimalEvent.AcceptingAnimalFailed.addingAnimalRejectedNow;
import static pl.csanecki.animalshelter.___.animal.AnimalEvent.AcceptingAnimalSucceeded.addingAnimalSucceededNow;
import static pl.csanecki.animalshelter.___.animal.AnimalEvent.AcceptingAnimalWarned.addingAnimalWarnedNow;

public class Shelter {

    private final ShelterLimits shelterLimits;
    private final Set<ShelterAnimal> shelterAnimals;
    private final Set<Species> acceptableSpecies;

    public Shelter(
            final ShelterLimits shelterLimits,
            final Set<ShelterAnimal> shelterAnimals,
            final Set<Species> acceptableSpecies
    ) {
        this.shelterLimits = shelterLimits;
        this.shelterAnimals = shelterAnimals;
        this.acceptableSpecies = acceptableSpecies;
    }

    Event accept(Animal animal) {
        if (shelterLimits.getCapacity() < shelterAnimals.length() + 1) {
            return addingAnimalRejectedNow("Capacity of shelter is exceeded");
        } else if (shelterLimits.getSafeThreshold() <= shelterAnimals.length() + 1) {
            return addingAnimalWarnedNow("Safe threshold of shelter is reached or exceeded");
        }
        return addingAnimalSucceededNow();
    }

    public boolean isSpeciesAcceptable(Species species) {
        return acceptableSpecies.contains(species);
    }

    public boolean safeThresholdExceededAfterAccepting(Animal animal) {
        return shelterLimits.getSafeThreshold() <= shelterAnimals.length() + 1;
    }

    public boolean capacityReachedAfterAccepting(Animal animal) {
        return shelterLimits.getCapacity() < shelterAnimals.length() + 1;
    }
}