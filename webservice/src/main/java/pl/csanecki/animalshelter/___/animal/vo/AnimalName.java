package pl.csanecki.animalshelter.___.animal.vo;

public final class AnimalName {

    private final String value;

    public AnimalName(final String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Animal name cannot be empty");
        }
        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }
}
