package pl.csanecki.animalshelter.webservice.model;

import pl.csanecki.animalshelter.domain.model.AnimalId;

public class AnimalFixture {

    public static AnimalId anyAnimalId() {
        return animalId(20);
    }

    public static AnimalId animalId(long id) {
        return AnimalId.of(id);
    }
}
