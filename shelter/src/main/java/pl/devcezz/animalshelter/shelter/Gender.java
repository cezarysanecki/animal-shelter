package pl.devcezz.animalshelter.shelter;

import io.vavr.collection.List;

enum Gender {
    Male, Female, NotSpecified;

    public static Gender of(String value) {
        if (value == null) {
            throw new IllegalArgumentException("gender cannot be null");
        }

        String trimmedValue = value.trim();
        if (trimmedValue.isEmpty()) {
            throw new IllegalArgumentException("gender cannot be empty");
        }

        return List.of(Gender.values())
                .find(animalGender -> animalGender.name().equals(trimmedValue))
                .getOrElseThrow(() -> new IllegalArgumentException("gender cannot be of value " + trimmedValue));
    }
}
