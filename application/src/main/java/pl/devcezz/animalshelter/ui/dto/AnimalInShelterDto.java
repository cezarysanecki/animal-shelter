package pl.devcezz.animalshelter.ui.dto;

import java.util.UUID;

public class AnimalInShelterDto {

    private final UUID animalId;
    private final String name;
    private final String species;
    private final Integer age;

    public AnimalInShelterDto(final UUID animalId, final String name, final String species, final Integer age) {
        this.animalId = animalId;
        this.name = name;
        this.species = species;
        this.age = age;
    }

    public UUID getAnimalId() {
        return animalId;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }

    public Integer getAge() {
        return age;
    }
}
