package pl.devcezz.shelter.catalogue;

import io.vavr.control.Option;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryCatalogueRepository implements CatalogueRepository {

    private final Map<AnimalId, Animal> database = new ConcurrentHashMap<>();

    @Override
    public Animal saveNew(Animal animal) {
        database.put(animal.getAnimalId(), animal);
        return animal;
    }

    @Override
    public Animal update(Animal animal) {
        if (database.containsKey(animal.getAnimalId())) {
            database.put(animal.getAnimalId(), animal);
        }
        return animal;
    }

    @Override
    public Animal updateStatus(Animal animal) {
        if (database.containsKey(animal.getAnimalId())) {
            database.put(animal.getAnimalId(), animal);
        }
        return animal;
    }

    @Override
    public Option<Animal> findBy(AnimalId animalId) {
        return Option.of(database.get(animalId));
    }
}
