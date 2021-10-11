package pl.devcezz.animalshelter.shelter;

abstract class ShelterAnimal {

    private final AnimalId animalId;
    private final AnimalInformation animalInformation;

    ShelterAnimal(final AnimalId animalId, final AnimalInformation animalInformation) {
        this.animalId = animalId;
        this.animalInformation = animalInformation;
    }

    AnimalId animalId() {
        return animalId;
    };

    AnimalInformation getAnimalInformation() {
        return animalInformation;
    };

    static class AdoptedAnimal extends ShelterAnimal {
        AdoptedAnimal(final AnimalId animalId, final AnimalInformation animalInformation) {
            super(animalId, animalInformation);
        }
    }

    static class AvailableAnimal extends ShelterAnimal {
        AvailableAnimal(final AnimalId animalId, final AnimalInformation animalInformation) {
            super(animalId, animalInformation);
        }
    }
}
