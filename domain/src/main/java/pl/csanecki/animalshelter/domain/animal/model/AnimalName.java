package pl.csanecki.animalshelter.domain.animal.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class AnimalName {

    String name;

    private AnimalName(@NonNull String name) {
        if (name.trim().length() < 3) {
            throw new IllegalArgumentException("Cannot use name shorter than 3 characters");
        }

        this.name = name.trim();
    }

    public static AnimalName of(String name) {
        return new AnimalName(name);
    }
}
