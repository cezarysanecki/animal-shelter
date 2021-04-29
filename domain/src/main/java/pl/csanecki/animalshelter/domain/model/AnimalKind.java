package pl.csanecki.animalshelter.domain.model;

import java.util.Arrays;

public enum AnimalKind {

    CAT, DOG;

    public static AnimalKind findAnimalKind(String name) {
        return Arrays.stream(values())
                .filter(value -> value.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
