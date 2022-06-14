package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import pl.devcezz.shelter.catalogue.exception.AnimalNotFound;
import pl.devcezz.shelter.shared.event.AnimalCreatedEvent;
import pl.devcezz.shelter.shared.event.AnimalDeletedEvent;

@Transactional("catalogueTransactionManager")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class AnimalFacade {

    private final AnimalRepository animalRepository;
    private final ApplicationEventPublisher eventPublisher;

    void save(Animal animal) {
        animalRepository.save(animal);

        eventPublisher.publishEvent(new AnimalCreatedEvent(animal.getAnimalId().getValue()));
    }

    void update(Animal animal) {
        Animal foundAnimal = animalRepository.findByAnimalId(animal.getAnimalId())
                .orElseThrow(() -> new AnimalNotFound(animal.getAnimalId().getValue()));

        animalRepository.save(Animal.ofExisting(
                foundAnimal.getId(),
                animal.getAnimalId(),
                animal.getName(),
                animal.getAge(),
                animal.getSpecies(),
                animal.getGender()
        ));
    }

    void delete(AnimalId animalId) {
        Animal foundAnimal = animalRepository.findByAnimalId(animalId)
                .orElseThrow(() -> new AnimalNotFound(animalId.getValue()));
        animalRepository.delete(foundAnimal);

        eventPublisher.publishEvent(new AnimalDeletedEvent(animalId.getValue()));
    }
}
