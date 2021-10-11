package pl.devcezz.animalshelter.shelter;

import io.vavr.control.Option;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AvailableAnimal;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent;

interface Animals {

    void save(Animal animal);

    void update(Animal animal);

    void delete(AnimalId animalId);

    Option<ShelterAnimal> findBy(AnimalId animalId);

    void adopt(AvailableAnimal animal);

    void publish(AnimalEvent event);
}
