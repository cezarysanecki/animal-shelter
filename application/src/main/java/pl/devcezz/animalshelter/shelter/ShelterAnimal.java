package pl.devcezz.animalshelter.shelter;

import pl.devcezz.animalshelter.shelter.model.AnimalId;

interface ShelterAnimal {

    AnimalId animalId();

    class AdoptedAnimal implements ShelterAnimal {

        private final AnimalId animalId;

        AdoptedAnimal(final AnimalId animalId) {
            this.animalId = animalId;
        }

        @Override
        public AnimalId animalId() {
            return animalId;
        }
    }

    class AvailableAnimal implements ShelterAnimal {

        private final AnimalId animalId;

        AvailableAnimal(final AnimalId animalId) {
            this.animalId = animalId;
        }

        @Override
        public AnimalId animalId() {
            return animalId;
        }
    }
}
