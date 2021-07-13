package pl.csanecki.animalshelter.___.species;

public final class Species {

    private final String value;

    public Species(final String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Species cannot be empty");
        }
        this.value = value.trim();
    }

    public String getValue() {
        return value;
    }
}
