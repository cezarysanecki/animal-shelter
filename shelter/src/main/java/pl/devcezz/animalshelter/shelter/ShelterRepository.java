package pl.devcezz.animalshelter.shelter;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AvailableAnimal;

interface ShelterRepository {

    ShelterLimits queryForShelterLimits();

    Set<AvailableAnimal> queryForAvailableAnimals();
}
