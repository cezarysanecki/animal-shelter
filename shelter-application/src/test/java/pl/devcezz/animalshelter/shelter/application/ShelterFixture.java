package pl.devcezz.animalshelter.shelter.application;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;

import java.util.UUID;

class ShelterFixture {

    static ShelterLimits shelterLimits(Integer capacity, Integer safeThreshold) {
        return new ShelterLimits(capacity, safeThreshold);
    }

    static Set<ShelterAnimal.AvailableAnimal> availableAnimals(int amount) {
        return Stream.fill(
                amount, () -> new ShelterAnimal.AvailableAnimal(new AnimalId(UUID.randomUUID()), animalInformation())
        ).toSet();
    }

    static AnimalId anyAnimalId() {
        return new AnimalId(UUID.randomUUID());
    }

    static AnimalInformation animalInformation() {
        return new AnimalInformation("Azor", "Dog", 6);
    }
}
