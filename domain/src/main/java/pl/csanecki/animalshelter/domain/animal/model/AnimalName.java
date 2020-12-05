package pl.csanecki.animalshelter.domain.animal.model;

import lombok.Value;

@Value
public class AnimalName {

    String name;

    private AnimalName(String name) {
        if (name.length() < 3) {
            throw new IllegalArgumentException("Cannot use name shorter than 3 characters");
        }

        this.name = name;
    }

    public static AnimalName of(String name) {
        return new AnimalName(name);
    }
}
