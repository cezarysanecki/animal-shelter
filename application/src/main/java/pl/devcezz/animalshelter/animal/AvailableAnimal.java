package pl.devcezz.animalshelter.animal;

public class AvailableAnimal implements ShelterAnimal {

    private final AnimalId animalId;

    public AvailableAnimal(final AnimalId animalId) {
        this.animalId = animalId;
    }

    @Override
    public AnimalId animalId() {
        return animalId;
    }
}
