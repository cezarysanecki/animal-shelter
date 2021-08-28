package pl.devcezz.animalshelter.animal.fixture;

import pl.devcezz.animalshelter.animal.AcceptAnimalCommand;
import pl.devcezz.animalshelter.animal.AdoptAnimalCommand;
import pl.devcezz.animalshelter.animal.Animal;
import pl.devcezz.animalshelter.animal.AnimalId;

import java.util.UUID;

public class AnimalFixture {

    public static Animal animal() {
        return new Animal(UUID.randomUUID(), "Azor", "Dog", 6);
    }

    public static Animal animal(AnimalId animalId) {
        return new Animal(animalId.value(), "Azor", "Dog", 6);
    }

    public static AcceptAnimalCommand acceptAnimalCommand() {
        return new AcceptAnimalCommand(UUID.randomUUID(), "Azor", "Dog", 6);
    }

    public static AdoptAnimalCommand adoptAnimalCommand(AnimalId animalId) {
        return new AdoptAnimalCommand(animalId.value());
    }

    public static AnimalId anyAnimalId() {
        return new AnimalId(UUID.randomUUID());
    }
}
