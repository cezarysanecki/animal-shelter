package pl.csanecki.animalshelter.domain.animal.model;

import lombok.NonNull;
import lombok.Value;

import java.util.Arrays;

@Value
public class AnimalKind {

    AvailableKind kind;

    private AnimalKind(@NonNull String kind) {
        this.kind = Arrays.stream(AvailableKind.values())
                .filter(availableKind -> availableKind.name().equals(kind))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Not valid kind of animal"));
    }

    public static AnimalKind of(String kind) {
        return new AnimalKind(kind);
    }

    public enum AvailableKind {
        DOG, CAT;
    }
}