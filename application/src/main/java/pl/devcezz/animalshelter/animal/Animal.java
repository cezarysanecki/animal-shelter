package pl.devcezz.animalshelter.animal;

import java.util.Objects;
import java.util.UUID;

public final class Animal {

    private final AnimalId id;
    private final Name name;
    private final Species species;
    private final Age age;

    public Animal(final UUID id, final String name, final String species, final Integer age) {
        this.id = new AnimalId(id);
        this.name = new Name(name);
        this.species = Species.of(species);
        this.age = new Age(age);
    }

    public record Name(String value) {

        public Name(final String value) {
            if (value == null) {
                throw new IllegalArgumentException("name cannot be null");
            }

            String trimmedValue = value.trim();
            if (trimmedValue.length() < 2 || trimmedValue.length() > 11) {
                throw new IllegalArgumentException("name must have size between 2 and 11");
            }
            this.value = trimmedValue;
        }
    }

    public record Age(Integer value) {

        public Age {
            if (value == null) {
                throw new IllegalArgumentException("age cannot be null");
            }
            if (value < 0) {
                throw new IllegalArgumentException("age cannot be negative");
            }
            if (value > 30) {
                throw new IllegalArgumentException("age cannot be grater than 30");
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Animal animal = (Animal) o;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}