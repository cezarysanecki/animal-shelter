package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.devcezz.shelter.catalogue.exception.AnimalNotFound;
import pl.devcezz.shelter.shared.event.AnimalCreatedEvent;
import pl.devcezz.shelter.shared.event.AnimalDeletedEvent;
import pl.devcezz.shelter.shared.infrastructure.CatalogueTransaction;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AnimalFacade {

    private final AnimalRepository animalRepository;
    private final ApplicationEventPublisher eventPublisher;

    @CatalogueTransaction
    public void save(UUID animalId, String name, Integer age, String species, String gender) {
        animalRepository.save(Animal.ofNew(
                AnimalId.of(animalId),
                name, age, species, gender));

        eventPublisher.publishEvent(new AnimalCreatedEvent(animalId));
    }

    @CatalogueTransaction
    public void update(UUID animalId, String name, Integer age, String species, String gender) {
        Animal foundAnimal = animalRepository.findByAnimalId(AnimalId.of(animalId))
                .orElseThrow(() -> new AnimalNotFound(animalId));

        animalRepository.save(Animal.ofExisting(
                foundAnimal.getId(),
                foundAnimal.getAnimalId(),
                name, age, species, gender));
    }

    @CatalogueTransaction
    public void delete(UUID animalId) {
        Animal foundAnimal = animalRepository.findByAnimalId(AnimalId.of(animalId))
                .orElseThrow(() -> new AnimalNotFound(animalId));
        animalRepository.delete(foundAnimal);

        eventPublisher.publishEvent(new AnimalDeletedEvent(animalId));
    }
}
