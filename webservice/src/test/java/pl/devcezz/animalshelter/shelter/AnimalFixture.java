package pl.devcezz.animalshelter.shelter;

import pl.devcezz.animalshelter.shelter.command.AcceptAnimalCommand;
import pl.devcezz.animalshelter.shelter.command.AdoptAnimalCommand;
import pl.devcezz.animalshelter.shelter.command.DeleteAnimalCommand;
import pl.devcezz.animalshelter.shelter.command.EditAnimalCommand;

import java.util.UUID;

class AnimalFixture {

    static Animal animal() {
        return new Animal(UUID.randomUUID(), "Azor", "Dog", 6);
    }

    static Animal animal(AnimalId animalId) {
        return new Animal(animalId.value(), "Azor", "Dog", 6);
    }

    static AcceptAnimalCommand acceptAnimalCommand() {
        return acceptAnimalCommand(UUID.randomUUID());
    }

    static AcceptAnimalCommand acceptAnimalCommand(UUID animalId) {
        return new AcceptAnimalCommand(animalId, "Azor", "Dog", 6);
    }

    static AdoptAnimalCommand adoptAnimalCommand(AnimalId animalId) {
        return new AdoptAnimalCommand(animalId.value());
    }

    static DeleteAnimalCommand deleteAnimalCommand(AnimalId animalId) {
        return new DeleteAnimalCommand(animalId.value());
    }

    static EditAnimalCommand editAnimalCommand(Animal animal, String newName) {
        return new EditAnimalCommand(animal.getId().value(), newName, animal.getSpecies().name(), animal.getAge().value());
    }

    static AnimalId anyAnimalId() {
        return new AnimalId(UUID.randomUUID());
    }

    static AnimalInformation animalInformation() {
        return new AnimalInformation("Azor", "Dog", 5);
    }
}
