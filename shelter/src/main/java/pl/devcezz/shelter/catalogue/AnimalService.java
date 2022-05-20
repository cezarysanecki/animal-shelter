package pl.devcezz.shelter.catalogue;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.devcezz.shelter.catalogue.exception.AnimalNotFound;
import pl.devcezz.shelter.catalogue.shared.event.AnimalCreatedEvent;
import pl.devcezz.shelter.catalogue.shared.event.AnimalDeletedEvent;

import javax.transaction.Transactional;

@RequiredArgsConstructor
class AnimalService {

    private final AnimalRepository animalRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    void save(Animal animal) {
        animalRepository.save(animal);

        eventPublisher.publishEvent(new AnimalCreatedEvent(animal.getAnimalId().getValue()));
    }

    @Transactional
    void update(Animal animal) {
        Animal foundAnimal = animalRepository.findByAnimalId(animal.getAnimalId())
                .orElseThrow(() -> new AnimalNotFound(animal.getAnimalId().getValue()));

        animalRepository.save(Animal.of(
                foundAnimal.getId(),
                animal.getAnimalId(),
                animal.getName(),
                animal.getAge(),
                animal.getSpecies(),
                animal.getGender()
        ));
    }

    @Transactional
    void delete(AnimalId animalId) {
        Animal foundAnimal = animalRepository.findByAnimalId(animalId)
                .orElseThrow(() -> new AnimalNotFound(animalId.getValue()));
        animalRepository.delete(foundAnimal);

        eventPublisher.publishEvent(new AnimalDeletedEvent(animalId.getValue()));
    }
}
