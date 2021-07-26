package pl.devcezz.animalshelter.animal;

import java.util.UUID;

class AnimalFixture {

    static Animal buildAnimal() {
        return new Animal(UUID.randomUUID(), "Azor", 5, "Dog");
    }

    static Animal buildAnimal(String name, Integer age, String species) {
        return new Animal(UUID.randomUUID(), name, age, species);
    }

    static Animal buildAnimalWithName(String name) {
        return buildAnimal(name, 5, "Dog");
    }

    static Animal buildAnimalWithAge(Integer age) {
        return buildAnimal("Azor", age, "Dog");
    }

    static Animal buildAnimalWithSpecies(String species) {
        return buildAnimal("Azor", 5, species);
    }
}
