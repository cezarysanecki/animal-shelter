package pl.devcezz.animalshelter.animal.fixture;

import pl.devcezz.animalshelter.animal.AcceptAnimalCommand;
import pl.devcezz.animalshelter.animal.Animal;

import java.util.UUID;

public class AnimalFixture {

    public static Animal animal() {
        return new Animal(UUID.randomUUID(), "Azor", "Dog", 6);
    }

    public static AcceptAnimalCommand acceptAnimalCommand() {
        return new AcceptAnimalCommand(UUID.randomUUID(), "Azor", "Dog", 6);
    }
}
