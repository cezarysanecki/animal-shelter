package pl.devcezz.animalshelter.animal;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.animal.model.AvailableAnimal;
import pl.devcezz.animalshelter.animal.model.ShelterLimits;

public interface ShelterRepository {

    ShelterLimits queryForShelterLimits();

    Set<AvailableAnimal> queryForAvailableAnimals();
}
