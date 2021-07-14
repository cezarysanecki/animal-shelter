package pl.csanecki.animalshelter.___.animal;

import pl.csanecki.animalshelter.___.species.Species;

import java.util.UUID;

public final class Animal {

    private final AnimalId animalId;
    private final Name name;
    private final Species species;
    private final Age age;

    Animal(final UUID id, final String name, final String species, final int age) {
        this.animalId = new AnimalId(id);
        this.name = new Name(name);
        this.species = new Species(species);
        this.age = new Age(age);
    }

    AnimalId getAnimalId() {
        return animalId;
    }

    public Name getName() {
        return name;
    }

    public Species getSpecies() {
        return species;
    }

    public Age getAge() {
        return age;
    }
}

final class Name {

    private final String value;

    Name(final String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }
}

final class Age {

    private final int value;

    Age(final int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}