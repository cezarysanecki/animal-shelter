package pl.devcezz.animalshelter.animal.fixture;

import pl.devcezz.animalshelter.animal.Animal;
import pl.devcezz.animalshelter.animal.AnimalId;

import java.util.UUID;

public class AnimalFixture {

    public static Animal animal() {
        return new Animal(UUID.randomUUID(), "Azor", "Dog", 6);
    }
}
