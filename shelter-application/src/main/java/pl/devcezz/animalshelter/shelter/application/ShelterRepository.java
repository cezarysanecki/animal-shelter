package pl.devcezz.animalshelter.shelter.application;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.shelter.application.ShelterAnimal.AvailableAnimal;

interface ShelterRepository {

    ShelterLimits queryForShelterLimits();

    Set<AvailableAnimal> queryForAvailableAnimals();
}
