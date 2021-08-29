package pl.devcezz.animalshelter.animal;

import io.vavr.collection.Set;

public interface ShelterRepository {

    ShelterLimits queryForShelterLimits();

    Set<AvailableAnimal> queryForAnimalsInShelter();
}
