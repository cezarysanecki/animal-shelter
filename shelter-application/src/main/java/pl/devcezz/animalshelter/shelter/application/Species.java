package pl.devcezz.animalshelter.shelter.application;

import io.vavr.collection.List;

enum Species {
    Dog, Cat;

    static Species of(String value) {
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
