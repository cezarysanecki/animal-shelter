package pl.csanecki.animalshelter.___.animal.vo;

import io.vavr.collection.List;

public enum AnimalSpecies {
    Dog, Cat;

    public static AnimalSpecies of(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Animal species cannot be null");
        }

        String trimmedValue = value.trim();
        if (trimmedValue.isEmpty()) {
            throw new IllegalArgumentException("Animal species cannot be empty");
        }

        return List.of(AnimalSpecies.values())
                .find(animalSpecies -> animalSpecies.name().equals(trimmedValue))
                .getOrElseThrow(() -> new IllegalArgumentException("Cannot accept animal of species: " + trimmedValue));
    }
}
