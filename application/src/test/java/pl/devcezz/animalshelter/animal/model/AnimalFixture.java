package pl.devcezz.animalshelter.animal.model;

import pl.devcezz.animalshelter.read.result.AnimalInfoDto;

import java.time.Instant;
import java.util.UUID;

public class AnimalFixture {

    public static Animal buildAnimal(String name, String species, Integer age) {
        return new Animal(UUID.randomUUID(), name, species, age);
    }

    public static Animal buildAnimal() {
        return buildAnimal("Azor", "Dog", 5);
    }

    public static Animal buildAnimalWithName(String name) {
        return buildAnimal(name, "Dog", 5);
    }

    public static Animal buildAnimalWithAge(Integer age) {
        return buildAnimal("Azor", "Dog", age);
    }

    public static Animal buildAnimalWithSpecies(String species) {
        return buildAnimal("Azor", species, 5);
    }

    public static AnimalId anyAnimalId() {
        return new AnimalId(UUID.randomUUID());
    }

    public static AnimalInfoDto animalInfo(UUID animalId) {
        return new AnimalInfoDto(animalId, "Azor", "Dog", 10, Instant.now(), null);
    }
}
