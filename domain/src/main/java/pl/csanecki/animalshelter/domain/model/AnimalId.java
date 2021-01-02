package pl.csanecki.animalshelter.domain.model;

import lombok.Value;

@Value
public class AnimalId {

    long animalId;

    private AnimalId(long animalId) {
        this.animalId = animalId;
    }

    public static AnimalId of(long id) {
        return new AnimalId(id);
    }
}
