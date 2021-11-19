package pl.devcezz.animalshelter.shelter.application;

import io.vavr.control.Option;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent;

interface Animals {

    void save(Animal animal);

    void update(Animal animal);

    void delete(AnimalId animalId);

    Option<ShelterAnimal> findBy(AnimalId animalId);

    void adopt(ShelterAnimal.AvailableAnimal animal);

    void publish(AnimalEvent event);
}
