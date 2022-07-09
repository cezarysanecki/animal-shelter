package pl.devcezz.shelter.catalogue;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import pl.devcezz.shelter.commons.model.Age;
import pl.devcezz.shelter.commons.model.Gender;
import pl.devcezz.shelter.commons.model.Name;
import pl.devcezz.shelter.commons.model.Species;

import static pl.devcezz.shelter.catalogue.AnimalIllegalStateException.exceptionCannotConfirmed;
import static pl.devcezz.shelter.catalogue.AnimalIllegalStateException.exceptionCannotDelete;
import static pl.devcezz.shelter.catalogue.AnimalIllegalStateException.exceptionCannotUpdate;
import static pl.devcezz.shelter.catalogue.Status.Confirmed;
import static pl.devcezz.shelter.catalogue.Status.Draft;

@Getter
@EqualsAndHashCode(of = "animalId")
class Animal {

    static Animal create(AnimalId animalId, Name name, Age age, Species species, Gender gender) {
        return new Animal(animalId, name, age, species, gender, Draft);
    }

    static Animal restore(AnimalId animalId, Name name, Age age, Species species, Gender gender, Status status) {
        return new Animal(animalId, name, age, species, gender, status);
    }

    @Id
    private Long id;
    private AnimalId animalId;
    private Name name;
    private Age age;
    private Species species;
    private Gender gender;
    private Status status;

    private Animal(AnimalId animalId, Name name, Age age, Species species, Gender gender, Status status) {
        this.animalId = animalId;
        this.name = name;
        this.age = age;
        this.species = species;
        this.gender = gender;
        this.status = status;
    }

    Animal confirm() {
        if (cannotBeModified()) {
            throw exceptionCannotConfirmed(animalId.getValue());
        }
        status = Confirmed;
        return this;
    }

    Animal updateFields(Name name, Age age, Species species, Gender gender) {
        if (cannotBeModified()) {
            throw exceptionCannotUpdate(animalId.getValue());
        }

        this.name = name;
        this.age = age;
        this.species = species;
        this.gender = gender;

        return this;
    }

    Animal delete() {
        if (cannotBeModified()) {
            throw exceptionCannotDelete(animalId.getValue());
        }
        return this;
    }

    private boolean cannotBeModified() {
        return status != Draft;
    }
}

enum Status {
    Draft, Confirmed
}

