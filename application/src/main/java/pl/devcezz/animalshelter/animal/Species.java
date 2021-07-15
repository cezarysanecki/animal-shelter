package pl.devcezz.animalshelter.animal;

import io.vavr.collection.List;

public enum Species {
    Dog, Cat;

    public static Species of(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Animal species cannot be null");
        }

        String trimmedValue = value.trim();
        if (trimmedValue.isEmpty()) {
            throw new IllegalArgumentException("Animal species cannot be empty");
        }

        return List.of(Species.values())
                .find(animalSpecies -> animalSpecies.name().equals(trimmedValue))
                .getOrElseThrow(() -> new IllegalArgumentException("Cannot accept animal of species: " + trimmedValue));
    }
}
