package pl.devcezz.animalshelter.shelter;

import pl.devcezz.animalshelter.read.result.AnimalInfoDto;

import java.time.Instant;
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

    static AnimalInfoDto animalInfo(UUID animalId) {
        return new AnimalInfoDto(animalId, "Azor", "Dog", 10, Instant.now(), null);
    }
}
