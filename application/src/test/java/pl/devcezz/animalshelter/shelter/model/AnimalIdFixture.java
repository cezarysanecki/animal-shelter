package pl.devcezz.animalshelter.shelter.model;

import java.util.UUID;

public class AnimalIdFixture {

    public static AnimalId anyAnimalId() {
        return new AnimalId(UUID.randomUUID());
    }
}
