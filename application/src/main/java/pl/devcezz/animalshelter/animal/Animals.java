package pl.devcezz.animalshelter.animal;

import io.vavr.control.Option;

public interface Animals {

    void save(Animal animal);

    Option<ShelterAnimal> findNotAdoptedBy(AnimalId animalId);

    void adopt(ShelterAnimal animal);

    void publish(AnimalEvent event);
}
