package pl.devcezz.animalshelter.___.animal;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.___.animal.vo.ShelterAnimal;
import pl.devcezz.animalshelter.___.animal.vo.ShelterLimits;

public interface ShelterRepository {

    void save(Animal animal);

    ShelterLimits queryForShelterLimits();

    Set<ShelterAnimal> queryForAnimalsInShelter();
}
