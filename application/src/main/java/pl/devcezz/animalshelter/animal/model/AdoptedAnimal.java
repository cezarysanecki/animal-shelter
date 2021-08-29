package pl.devcezz.animalshelter.animal.model;

public class AdoptedAnimal implements ShelterAnimal {

    private final AnimalId animalId;

    public AdoptedAnimal(final AnimalId animalId) {
        this.animalId = animalId;
    }

    @Override
    public AnimalId animalId() {
        return animalId;
    }
}
