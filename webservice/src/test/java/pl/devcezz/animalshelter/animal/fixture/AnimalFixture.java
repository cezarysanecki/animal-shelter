package pl.devcezz.animalshelter.animal.fixture;

import pl.devcezz.animalshelter.animal.command.AcceptAnimalCommand;
import pl.devcezz.animalshelter.animal.command.AdoptAnimalCommand;
import pl.devcezz.animalshelter.animal.command.DeleteAnimalCommand;
import pl.devcezz.animalshelter.animal.model.Animal;
import pl.devcezz.animalshelter.animal.model.AnimalId;

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

    public static DeleteAnimalCommand deleteAnimalCommand(AnimalId animalId) {
        return new DeleteAnimalCommand(animalId.value());
    }

    public static AnimalId anyAnimalId() {
        return new AnimalId(UUID.randomUUID());
    }
}
