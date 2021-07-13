package pl.csanecki.animalshelter.___;

import pl.csanecki.animalshelter.___.animal.AnimalId;
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

    Name getName() {
        return name;
    }

    Species getSpecies() {
        return species;
    }

    Age getAge() {
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

    String getValue() {
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

    int getValue() {
        return value;
    }
}