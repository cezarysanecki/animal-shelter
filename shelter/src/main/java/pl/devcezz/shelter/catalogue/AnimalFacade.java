package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.commons.events.DomainEvents;

import java.util.UUID;

import static pl.devcezz.shelter.catalogue.AnimalEvent.AnimalCreatedEvent.animalCreatedNow;
import static pl.devcezz.shelter.catalogue.AnimalEvent.AnimalDeletedEvent.animalDeletedNow;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
class AnimalFacade {

    private final AnimalRepository animalRepository;
    private final DomainEvents publisher;

    void save(UUID animalUuidId, String name, Integer age, String species, String gender) {
        animalRepository.saveNew(Animal.ofNew(
                animalUuidId, name, age, species, gender));

        publisher.publish(
                animalCreatedNow(AnimalId.of(animalUuidId)));
    }

    void update(UUID animalUuidId, String name, Integer age, String species, String gender) {
        Animal animal = animalRepository.findByAnimalId(AnimalId.of(animalUuidId))
                .getOrElseThrow(() -> new AnimalNotFoundException(animalUuidId));

        animal.updateFields(name, age, species, gender);

        animalRepository.update(animal);
    }

    void delete(UUID animalUuidId) {
        Animal animal = animalRepository.findByAnimalId(AnimalId.of(animalUuidId))
                .getOrElseThrow(() -> new AnimalNotFoundException(animalUuidId));

        animal.delete();
        animalRepository.updateStatus(animal);

        publisher.publish(
                animalDeletedNow(AnimalId.of(animalUuidId)));
    }
}
