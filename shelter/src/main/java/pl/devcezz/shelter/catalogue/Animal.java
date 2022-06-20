package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pl.devcezz.shelter.catalogue.exception.AnimalIllegalStateException;

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
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Stream;

import static pl.devcezz.shelter.catalogue.exception.AnimalIllegalStateException.exceptionCannotUpdate;

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

    private enum Status {
        DELETED, REGISTERED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "animal_id"))
    private AnimalId animalId;

    private String name;
    private Integer age;
    private String species;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private Instant creationTimestamp;

    @UpdateTimestamp
    private Instant modificationTimestamp;

    private Animal(AnimalId animalId, String name, Integer age, String species, String gender) {
        this.animalId = animalId;
        this.name = name;
        this.age = age;
        this.species = species;
        this.gender = Gender.of(gender);
    }

    static Animal ofNew(AnimalId animalId, String name, Integer age, String species, String gender) {
        return new Animal(animalId, name, age, species, gender);
    }

    void updateFields(String name, Integer age, String species, String gender) {
        if (cannotBeChanged()) {
            throw exceptionCannotUpdate(animalId.getValue());
        }

        this.name = name;
        this.age = age;
        this.species = species;
        this.gender = Gender.of(gender);
    }

    boolean cannotBeChanged() {
        return status != null;
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