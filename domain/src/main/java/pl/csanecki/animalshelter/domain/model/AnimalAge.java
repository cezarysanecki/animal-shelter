package pl.csanecki.animalshelter.domain.model;

import lombok.Value;

@Value
public class AnimalAge {

    int animalAge;

    private AnimalAge(int animalAge) {
        if (animalAge < 0) {
            throw new IllegalArgumentException("Cannot use negative integer as number of ages");
        }

        this.animalAge = animalAge;
    }

    public static AnimalAge of(int age) {
        return new AnimalAge(age);
    }
}
