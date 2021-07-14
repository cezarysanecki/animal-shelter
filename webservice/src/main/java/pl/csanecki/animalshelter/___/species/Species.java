package pl.csanecki.animalshelter.___.species;

import java.util.Objects;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Species species = (Species) o;
        return Objects.equals(value, species.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
