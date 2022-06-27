package pl.devcezz.shelter.catalogue;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.devcezz.shelter.commons.commands.Result;
import pl.devcezz.shelter.commons.events.DomainEvents;

import java.util.UUID;

import static pl.devcezz.shelter.catalogue.AnimalEvent.AnimalCreatedEvent.animalCreatedNow;
import static pl.devcezz.shelter.catalogue.AnimalEvent.AnimalDeletedEvent.animalDeletedNow;
import static pl.devcezz.shelter.commons.commands.Result.Rejection;
import static pl.devcezz.shelter.commons.commands.Result.Success;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class Catalogue {

    private final CatalogueDatabase database;
    private final DomainEvents publisher;

    public Try<Result> addNewAnimal(UUID animalUuidId, String name, Integer age, String species, String gender) {
        return Try.of(() -> Option.of(Animal.ofNew(animalUuidId, name, age, species, gender))
                .map(this::saveAndPublishEvent)
                .map(savedAnimal -> Success)
                .getOrElse(Rejection)
        ).onFailure(ex -> log.error("failed to add new animal", ex));
    }

    private Animal saveAndPublishEvent(Animal animal) {
        database.saveNew(animal);
        publisher.publish(animalCreatedNow(animal.getAnimalId()));
        return animal;
    }

    public Try<Result> updateExistingAnimal(UUID animalUuidId, String name, Integer age, String species, String gender) {
        return Try.of(() -> database.findByAnimalId(AnimalId.of(animalUuidId))
                .map(animal -> animal.updateFields(name, age, species, gender))
                .map(database::update)
                .map(updatedAnimal -> Success)
                .getOrElse(Rejection)
        ).onFailure(ex -> log.error("failed to update animal", ex));
    }

    public Try<Result> removeExistingAnimal(UUID animalUuidId) {
        return Try.of(() -> database.findByAnimalId(AnimalId.of(animalUuidId))
                .map(Animal::delete)
                .map(this::removeAndPublishEvent)
                .map(removedAnimal -> Success)
                .getOrElse(Rejection)
        ).onFailure(ex -> log.error("failed to remove animal", ex));
    }

    private Animal removeAndPublishEvent(Animal animal) {
        database.updateStatus(animal);
        publisher.publish(animalDeletedNow(animal.getAnimalId()));
        return animal;
    }
}
