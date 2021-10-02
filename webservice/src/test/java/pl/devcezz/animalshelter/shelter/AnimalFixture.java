package pl.devcezz.animalshelter.shelter;

import pl.devcezz.animalshelter.shelter.command.AcceptAnimalCommand;
import pl.devcezz.animalshelter.shelter.command.AdoptAnimalCommand;
import pl.devcezz.animalshelter.shelter.command.DeleteAnimalCommand;
import pl.devcezz.animalshelter.shelter.vo.AnimalId;

import java.util.UUID;

class AnimalFixture {

    static Animal animal() {
        return new Animal(UUID.randomUUID(), "Azor", "Dog", 6);
    }

    static Animal animal(AnimalId animalId) {
        return new Animal(animalId.value(), "Azor", "Dog", 6);
    }

    static AcceptAnimalCommand acceptAnimalCommand() {
        return new AcceptAnimalCommand(UUID.randomUUID(), "Azor", "Dog", 6);
    }

    static AdoptAnimalCommand adoptAnimalCommand(AnimalId animalId) {
        return new AdoptAnimalCommand(animalId.value());
    }

    static DeleteAnimalCommand deleteAnimalCommand(AnimalId animalId) {
        return new DeleteAnimalCommand(animalId.value());
    }

    static AnimalId anyAnimalId() {
        return new AnimalId(UUID.randomUUID());
    }
}
