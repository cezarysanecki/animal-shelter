package pl.devcezz.animalshelter.shelter.vo;

import java.util.UUID;

public class AnimalIdFixture {

    public static AnimalId anyAnimalId() {
        return new AnimalId(UUID.randomUUID());
    }
}
