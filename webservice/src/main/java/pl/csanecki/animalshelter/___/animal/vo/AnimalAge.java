package pl.csanecki.animalshelter.___.animal.vo;

public final class AnimalAge {

    private final int value;

    public AnimalAge(final int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Animal age cannot be negative");
        }
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
