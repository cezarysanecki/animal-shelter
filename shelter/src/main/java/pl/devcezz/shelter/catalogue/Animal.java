package pl.devcezz.shelter.catalogue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

import static pl.devcezz.shelter.catalogue.AnimalIllegalStateException.exceptionCannotDelete;
import static pl.devcezz.shelter.catalogue.AnimalIllegalStateException.exceptionCannotUpdate;

@Getter
@EqualsAndHashCode(of = "animalId")
class Animal {

    @Id
    private Long id;
    private AnimalId animalId;
    private String name;
    private Integer age;
    private String species;
    private Gender gender;
    private Status status;

    private Animal(AnimalId animalId, String name, Integer age, String species, Gender gender, Status status) {
        this.animalId = animalId;
        this.name = name;
        this.age = age;
        this.species = species;
        this.gender = gender;
        this.status = status;
    }

    static Animal ofNew(UUID animalId, String name, Integer age, String species, String gender) {
        return new Animal(AnimalId.of(animalId), name, age, species, Gender.of(gender), null);
    }

    static Animal of(UUID animalId, String name, Integer age, String species, Gender gender, Status status) {
        return new Animal(AnimalId.of(animalId), name, age, species, gender, status);
    }

    Animal updateFields(String name, Integer age, String species, String gender) {
        if (cannotBeChanged()) {
            throw exceptionCannotUpdate(animalId.getValue());
        }

        this.name = name;
        this.age = age;
        this.species = species;
        this.gender = Gender.of(gender);

        return this;
    }

    Animal register() {
        return of(animalId.getValue(), name, age, species, gender, Status.REGISTERED);
    }

    Animal delete() {
        if (cannotBeChanged()) {
            throw exceptionCannotDelete(animalId.getValue());
        }
        return of(animalId.getValue(), name, age, species, gender, Status.DELETED);
    }

    private boolean cannotBeChanged() {
        return status != null;
    }
}

enum Status {
    DELETED, REGISTERED
}

enum Gender {
    MALE, FEMALE;

    static Gender of(String gender) {
        return Stream.of(values())
                .filter(value -> value.name().equalsIgnoreCase(gender))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("gender can be only: " + Arrays.toString(values())));
    }
}