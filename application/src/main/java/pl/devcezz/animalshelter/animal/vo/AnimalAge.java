package pl.devcezz.animalshelter.animal.vo;

public record AnimalAge(int value) {

    public AnimalAge {
        if (value < 0) {
            throw new IllegalArgumentException("Animal age cannot be negative");
        }
    }
}
