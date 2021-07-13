package pl.csanecki.animalshelter.___.animal;

import java.util.UUID;

public final class AnimalId {

    private final UUID animalId;

    public AnimalId(final UUID animalId) {
        if (animalId == null) {
            throw new IllegalArgumentException("AnimalId cannot be null");
        }
        this.animalId = animalId;
    }

    public UUID getAnimalId() {
        return animalId;
    }
}
