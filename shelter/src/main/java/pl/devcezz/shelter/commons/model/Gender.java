package pl.devcezz.shelter.commons.model;

import java.util.Arrays;
import java.util.stream.Stream;

public enum Gender {
    Male, Female;

    public static Gender of(String gender) {
        return Stream.of(values())
                .filter(value -> value.name().equalsIgnoreCase(gender))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("gender can be only: " + Arrays.toString(values())));
    }
}