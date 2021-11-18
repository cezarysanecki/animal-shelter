package pl.devcezz.animalshelter.shelter.read.dto;

import java.time.Instant;
import java.util.UUID;

public class AnimalInfoDto {

    private final UUID animalId;
    private final String name;
    private final String species;
    private final Integer age;
    private final String gender;
    private final Instant admittedAt;
    private final Instant adoptedAt;

    public AnimalInfoDto(final UUID animalId, final String name, final String species, final Integer age, final String gender, final Instant admittedAt, final Instant adoptedAt) {
        this.animalId = animalId;
        this.name = name;
        this.species = species;
        this.age = age;
        this.gender = gender;
        this.admittedAt = admittedAt;
        this.adoptedAt = adoptedAt;
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

    public String getGender() {
        return gender;
    }

    public Instant getAdmittedAt() {
        return admittedAt;
    }

    public Instant getAdoptedAt() {
        return adoptedAt;
    }
}
