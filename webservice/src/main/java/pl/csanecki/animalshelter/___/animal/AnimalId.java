package pl.csanecki.animalshelter.___.animal;

import java.util.UUID;

public final class AnimalId {

    private final UUID value;

    public AnimalId(final UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("AnimalId cannot be null");
        }
        this.value = value;
    }

    public UUID getValue() {
        return value;
    }
}
