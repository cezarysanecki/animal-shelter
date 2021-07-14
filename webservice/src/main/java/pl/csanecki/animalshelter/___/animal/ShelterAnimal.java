package pl.csanecki.animalshelter.___.animal;

import java.util.UUID;

public class ShelterAnimal {

    private final AnimalId animalId;

    public ShelterAnimal(final UUID id) {
        this.animalId = new AnimalId(id);
    }

    public AnimalId getAnimalId() {
        return animalId;
    }
}
