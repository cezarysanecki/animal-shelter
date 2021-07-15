package pl.devcezz.animalshelter.___.animal.vo;

public record AnimalName(String value) {

    public AnimalName(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("Animal name cannot be null");
        }

        String trimmedValue = value.trim();
        if (trimmedValue.isEmpty()) {
            throw new IllegalArgumentException("Animal name cannot be empty");
        }
        this.value = trimmedValue;
    }
}
