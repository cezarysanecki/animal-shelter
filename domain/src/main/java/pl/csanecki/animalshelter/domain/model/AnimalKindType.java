package pl.csanecki.animalshelter.domain.model;

import io.vavr.collection.List;

public enum AnimalKindType {

    CAT, DOG;

    public static boolean isNotValid(String kind) {
        return List.of(AnimalKindType.values())
                .filter(type -> type.name().equals(kind))
                .isEmpty();
    }
}
