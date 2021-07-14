package pl.csanecki.animalshelter.___.animal;

import io.vavr.collection.Set;
import pl.csanecki.animalshelter.___.animal.vo.ShelterLimits;

public interface ShelterRepository {

    void save(Animal animal);

    ShelterLimits queryForShelterLimits();

    Set<ShelterAnimal> queryForAnimalsInShelter();
}
