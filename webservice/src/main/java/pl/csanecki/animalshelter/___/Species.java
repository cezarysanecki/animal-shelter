package pl.csanecki.animalshelter.___;

public final class Species {

    private final String value;

    private Species(final String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Species cannot be empty");
        }
        this.value = value.trim();
    }

    public static Species of(String value) {
        return new Species(value);
    }

    public String getValue() {
        return value;
    }
}
