package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.devcezz.shelter.catalogue.exception.AnimalNotFoundException;
import pl.devcezz.shelter.shared.event.AnimalCreatedEvent;
import pl.devcezz.shelter.shared.event.AnimalDeletedEvent;
import pl.devcezz.shelter.shared.infrastructure.CatalogueTransaction;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AnimalFacade {

    private final AnimalRepository animalRepository;
    private final ApplicationEventPublisher eventPublisher;

    @CatalogueTransaction
    public void save(UUID animalUuidId, String name, Integer age, String species, String gender) {
        animalRepository.save(Animal.ofNew(
                AnimalId.of(animalUuidId),
                name, age, species, gender));

        eventPublisher.publishEvent(new AnimalCreatedEvent(animalUuidId));
    }

    @CatalogueTransaction
    public void update(UUID animalUuidId, String name, Integer age, String species, String gender) {
        Animal foundAnimal = animalRepository.findByAnimalId(AnimalId.of(animalUuidId))
                .orElseThrow(() -> new AnimalNotFoundException(animalUuidId));

        foundAnimal.updateFields(name, age, species, gender);
    }

    @CatalogueTransaction
    public void delete(UUID animalUuidId) {
        Animal foundAnimal = animalRepository.findByAnimalId(AnimalId.of(animalUuidId))
                .orElseThrow(() -> new AnimalNotFoundException(animalUuidId));
        animalRepository.deleteAnimalDataFor(foundAnimal.getAnimalId());

        eventPublisher.publishEvent(
                new AnimalDeletedEvent(animalUuidId));
    }
}
