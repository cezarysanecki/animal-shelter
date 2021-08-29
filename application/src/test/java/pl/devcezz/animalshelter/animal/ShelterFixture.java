package pl.devcezz.animalshelter.animal;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;

import java.util.UUID;

class ShelterFixture {

    static ShelterLimits shelterLimits(Integer capacity, Integer safeThreshold) {
        return new ShelterLimits(capacity, safeThreshold);
    }

    static Shelter shelter(ShelterLimits shelterLimits, Integer amountOfAnimals) {
        return new Shelter(shelterLimits, availableAnimals(amountOfAnimals));
    }

    static Set<AvailableAnimal> availableAnimals(int amount) {
        return Stream.fill(amount, () -> new AvailableAnimal(new AnimalId(UUID.randomUUID()))).toSet();
    }
}
