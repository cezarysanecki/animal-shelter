package pl.csanecki.animalshelter.___.animal;

import pl.devcezz.cqrs.command.Command;

import java.util.UUID;

public class AcceptAnimalCommand implements Command {

    private final UUID animalId;
    private final String name;
    private final String species;
    private final int age;

    public AcceptAnimalCommand(final UUID animalId, final String name, final String species, final int age) {
        this.animalId = animalId;
        this.name = name;
        this.species = species;
        this.age = age;
    }

    UUID getAnimalId() {
        return animalId;
    }

    String getName() {
        return name;
    }

    String getSpecies() {
        return species;
    }

    int getAge() {
        return age;
    }
}
