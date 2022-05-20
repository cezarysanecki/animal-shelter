package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.stream.Stream;

@Entity
@Access(AccessType.FIELD)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Animal {

    private enum Gender {
        MALE, FEMALE;

        private static Gender of(String gender) {
            return Stream.of(values())
                    .filter(value -> value.name().equalsIgnoreCase(gender))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("gender can be only: " + Arrays.toString(values())));
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "animalId"))
    private AnimalId animalId;

    private String name;
    private Integer age;
    private String species;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    private Animal(AnimalId animalId, String name, Integer age, String species, String gender) {
        this.animalId = animalId;
        this.name = name;
        this.age = age;
        this.species = species;
        this.gender = Gender.of(gender);
    }

    private Animal(Long id, AnimalId animalId, String name, Integer age, String species, String gender) {
        this(animalId, name, age, species, gender);
        this.id = id;
    }

    static Animal of(AnimalId animalId, String name, Integer age, String species, String gender) {
        return new Animal(animalId, name, age, species, gender);
    }

    static Animal of(Long id, AnimalId animalId, String name, Integer age, String species, String gender) {
        return new Animal(id, animalId, name, age, species, gender);
    }

    Long getId() {
        return id;
    }

    AnimalId getAnimalId() {
        return animalId;
    }

    String getName() {
        return name;
    }

    Integer getAge() {
        return age;
    }

    String getSpecies() {
        return species;
    }

    String getGender() {
        return gender.name();
    }
}