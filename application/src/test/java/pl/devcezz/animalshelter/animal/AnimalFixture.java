package pl.devcezz.animalshelter.animal;

import pl.devcezz.animalshelter.animal.model.Animal;
import pl.devcezz.animalshelter.animal.model.AnimalId;

import java.util.UUID;

class AnimalFixture {

    static Animal buildAnimal(String name, String species, Integer age) {
        return new Animal(UUID.randomUUID(), name, species, age);
    }

    static Animal buildAnimal() {
        return buildAnimal("Azor", "Dog", 5);
    }

    static Animal buildAnimalWithName(String name) {
        return buildAnimal(name, "Dog", 5);
    }

    static Animal buildAnimalWithAge(Integer age) {
        return buildAnimal("Azor", "Dog", age);
    }

    static Animal buildAnimalWithSpecies(String species) {
        return buildAnimal("Azor", species, 5);
    }

    static AnimalId anyAnimalId() {
        return new AnimalId(UUID.randomUUID());
    }
}
