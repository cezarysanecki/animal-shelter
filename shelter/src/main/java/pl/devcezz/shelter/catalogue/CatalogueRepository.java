package pl.devcezz.shelter.catalogue;

import io.vavr.control.Option;

public interface CatalogueRepository {

    Animal saveNew(Animal animal);

    Animal update(Animal animal);

    Animal updateStatus(Animal animal);

    Option<Animal> findBy(AnimalId animalId);
}
