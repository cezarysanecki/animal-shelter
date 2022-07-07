package pl.devcezz.shelter.catalogue;

import io.vavr.control.Option;

interface CatalogueRepository {

    Option<Animal> findBy(AnimalId animalId);

    Animal save(Animal animal);

    Animal update(Animal animal);

    Animal delete(Animal animal);
}
