package pl.devcezz.animalshelter.animal;

import io.vavr.control.Option;

public interface Animals {

    void save(Animal animal);

    Option<ShelterAnimal> findBy(AnimalId animalId);

    void adopt(AvailableAnimal animal);

    void publish(AnimalEvent event);
}
