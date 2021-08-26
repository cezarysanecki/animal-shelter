package pl.devcezz.animalshelter.animal;

import java.util.Objects;
import java.util.UUID;

public record AnimalId(UUID value) {

    public AnimalId {
        if (value == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AnimalId animalId = (AnimalId) o;
        return Objects.equals(value, animalId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
