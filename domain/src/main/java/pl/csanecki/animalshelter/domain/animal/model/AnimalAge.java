package pl.csanecki.animalshelter.domain.animal.model;

import lombok.Value;

@Value
public class AnimalAge {

    int age;

    private AnimalAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Cannot use negative integer as number of ages");
        }

        this.age = age;
    }

    public static AnimalAge of(int age) {
        return new AnimalAge(age);
    }
}
