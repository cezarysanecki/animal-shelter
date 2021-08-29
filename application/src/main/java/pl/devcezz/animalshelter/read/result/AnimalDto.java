package pl.devcezz.animalshelter.read.result;

import java.util.UUID;

public class AnimalDto {

    private final UUID animalId;
    private final String name;
    private final String species;
    private final Integer age;
    private final Boolean inShelter;

    public AnimalDto(final UUID animalId, final String name, final String species, final Integer age, final Boolean inShelter) {
        this.animalId = animalId;
        this.name = name;
        this.species = species;
        this.age = age;
        this.inShelter = inShelter;
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

    public Boolean getInShelter() {
        return inShelter;
    }
}
