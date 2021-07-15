package pl.devcezz.animalshelter.animal;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.animal.vo.ShelterAnimal;
import pl.devcezz.animalshelter.animal.vo.ShelterLimits;

public interface ShelterRepository {

    ShelterLimits queryForShelterLimits();

    Set<ShelterAnimal> queryForAnimalsInShelter();
}
