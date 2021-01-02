package pl.csanecki.animalshelter.domain.model;

import lombok.Value;

@Value
public class AnimalKind {

    String animalKind;

    private AnimalKind(String animalKind) {
        if (AnimalKindType.isNotValid(animalKind)) {
            throw new IllegalArgumentException("Not valid kind of animal");
        }

        this.animalKind = animalKind;
    }

    public static AnimalKind of(String kind) {
        return new AnimalKind(kind);
    }
}
