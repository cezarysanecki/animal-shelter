package pl.csanecki.animalshelter.___.animal;

import pl.csanecki.animalshelter.___.animal.vo.AnimalAge;
import pl.csanecki.animalshelter.___.animal.vo.AnimalName;
import pl.csanecki.animalshelter.___.species.Species;

import java.util.UUID;

public final class Animal {

    private final AnimalId id;
    private final AnimalName name;
    private final Species species;
    private final AnimalAge age;

    public Animal(final UUID id, final String name, final String species, final int age) {
        this.id = new AnimalId(id);
        this.name = new AnimalName(name);
        this.species = new Species(species);
        this.age = new AnimalAge(age);
    }

    public AnimalId getId() {
        return id;
    }

    public AnimalName getName() {
        return name;
    }

    public Species getSpecies() {
        return species;
    }

    public AnimalAge getAge() {
        return age;
    }
}