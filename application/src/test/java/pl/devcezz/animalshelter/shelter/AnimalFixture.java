package pl.devcezz.animalshelter.shelter;

import java.util.UUID;

class AnimalFixture {

    static Animal animal(String name, String species, Integer age) {
        return new Animal(UUID.randomUUID(), name, species, age);
    }

    static Animal animal() {
        return animal("Azor", "Dog", 5);
    }
}
