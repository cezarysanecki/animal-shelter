package pl.devcezz.animalshelter.animal;

import java.util.UUID;

public final class Animal {

    private final AnimalId id;
    private final Name name;
    private final Age age;
    private final Species species;

    public Animal(final UUID id, final String name, final Integer age, final String species) {
        this.id = new AnimalId(id);
        this.name = new Name(name);
        this.age = new Age(age);
        this.species = Species.of(species);
    }

    public record Name(String value) {

        public Name(final String value) {
            if (value == null) {
                throw new IllegalArgumentException("Animal name cannot be null");
            }

            String trimmedValue = value.trim();
            if (trimmedValue.length() < 2 || trimmedValue.length() > 11) {
                throw new IllegalArgumentException("Animal name must have size between 2 and 11");
            }
            this.value = trimmedValue;
        }
    }

    public record Age(Integer value) {

        public Age {
            if (value == null) {
                throw new IllegalArgumentException("Animal age cannot be null");
            }
            if (value < 0) {
                throw new IllegalArgumentException("Animal age cannot be negative");
            }
            if (value > 30) {
                throw new IllegalArgumentException("Animal age cannot be grater than 30");
            }
        }
    }

    public AnimalId getId() {
        return id;
    }

    public Name getName() {
        return name;
    }

    public Age getAge() {
        return age;
    }

    public Species getSpecies() {
        return species;
    }
}