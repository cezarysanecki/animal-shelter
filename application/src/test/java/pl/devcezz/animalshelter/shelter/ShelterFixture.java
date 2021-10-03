package pl.devcezz.animalshelter.shelter;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import pl.devcezz.animalshelter.shelter.model.AnimalId;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AvailableAnimal;

import java.util.UUID;

class ShelterFixture {

    static ShelterLimits shelterLimits(Integer capacity, Integer safeThreshold) {
        return new ShelterLimits(capacity, safeThreshold);
    }

    static Shelter shelter(ShelterLimits shelterLimits, Integer amountOfAnimals) {
        return new Shelter(shelterLimits, availableAnimals(amountOfAnimals));
    }

    private static Set<AvailableAnimal> availableAnimals(int amount) {
        return Stream.fill(amount, () -> new AvailableAnimal(new AnimalId(UUID.randomUUID()))).toSet();
    }

    static AnimalId anyAnimalId() {
        return new AnimalId(UUID.randomUUID());
    }
}
