package pl.devcezz.shelter.catalogue;

import pl.devcezz.shelter.InMemoryRepository;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCatalogueRepository extends InMemoryRepository<Animal, Long> implements AnimalRepository {

    private final AtomicLong idSequence = new AtomicLong();

    @Override
    public <S extends Animal> S save(S entity) {
        if (entity.getId() == null) {
            setId(entity);
        }
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<Animal> findByAnimalId(AnimalId animalId) {
        return entities.values()
                .stream()
                .filter(animal -> animal.getAnimalId().equals(animalId))
                .findFirst();
    }

    private <S extends Animal> void setId(S entity) {
        try {
            Class<? extends Animal> clazz = entity.getClass();
            Field id = clazz.getDeclaredField("id");
            id.setAccessible(true);
            id.set(entity, idSequence.incrementAndGet());
        } catch (Exception e) {
            throw new IllegalStateException("cannot set id", e);
        }
    }

}
