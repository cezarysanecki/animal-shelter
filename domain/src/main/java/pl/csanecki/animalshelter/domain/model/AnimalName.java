package pl.csanecki.animalshelter.domain.model;

import lombok.Value;

@Value
public class AnimalName {

    public static final int MIN_LENGTH = 3;
    public static final int MAX_LENGTH = 25;

    private static final String ERROR_MESSAGE = String.format(
            "Cannot use name shorter than %d or longer than %d charactes", MIN_LENGTH, MAX_LENGTH
    );

    String animalName;

    private AnimalName(String animalName) {
        if (animalName.length() < MIN_LENGTH || animalName.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }

        this.animalName = animalName;
    }

    public static AnimalName of(String name) {
        return new AnimalName(name);
    }
}
