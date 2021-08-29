package pl.devcezz.animalshelter.animal;

import io.vavr.control.Option;
import pl.devcezz.animalshelter.animal.event.AnimalEvent;
import pl.devcezz.animalshelter.animal.model.Animal;
import pl.devcezz.animalshelter.animal.model.AnimalId;
import pl.devcezz.animalshelter.animal.model.AvailableAnimal;
import pl.devcezz.animalshelter.animal.model.ShelterAnimal;

public interface Animals {

    void save(Animal animal);

    void update(Animal animal);

    Option<ShelterAnimal> findBy(AnimalId animalId);

    void adopt(AvailableAnimal animal);

    void publish(AnimalEvent event);
}
