package pl.devcezz.animalshelter.animal.model;

import io.vavr.collection.List;

public enum Species {
    Dog, Cat;

    public static Species of(String value) {
        if (value == null) {
            throw new IllegalArgumentException("species cannot be null");
        }

        String trimmedValue = value.trim();
        if (trimmedValue.isEmpty()) {
            throw new IllegalArgumentException("species cannot be empty");
        }

        return List.of(Species.values())
                .find(animalSpecies -> animalSpecies.name().equals(trimmedValue))
                .getOrElseThrow(() -> new IllegalArgumentException("species cannot be of value " + trimmedValue));
    }
}
