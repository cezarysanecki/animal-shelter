package pl.devcezz.animalshelter.___.animal;

import pl.devcezz.animalshelter.___.animal.vo.AnimalAge;
import pl.devcezz.animalshelter.___.animal.vo.AnimalId;
import pl.devcezz.animalshelter.___.animal.vo.AnimalName;
import pl.devcezz.animalshelter.___.animal.vo.AnimalSpecies;

import java.util.UUID;

public final class Animal {

    private final AnimalId id;
    private final AnimalName name;
    private final AnimalAge age;
    private final AnimalSpecies animalSpecies;

    public Animal(final UUID id, final String name, final int age, final String species) {
        this.id = new AnimalId(id);
        this.name = new AnimalName(name);
        this.age = new AnimalAge(age);
        this.animalSpecies = AnimalSpecies.of(species);
    }

    public AnimalId getId() {
        return id;
    }

    public AnimalName getName() {
        return name;
    }

    public AnimalAge getAge() {
        return age;
    }

    public AnimalSpecies getSpecies() {
        return animalSpecies;
    }
}