package pl.devcezz.shelter.catalogue;

import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.devcezz.shelter.commons.commands.Result;
import pl.devcezz.shelter.commons.events.DomainEvents;
import pl.devcezz.shelter.commons.model.Age;
import pl.devcezz.shelter.commons.model.Gender;
import pl.devcezz.shelter.commons.model.Name;
import pl.devcezz.shelter.commons.model.Species;

import static pl.devcezz.shelter.catalogue.CatalogueEvent.AnimalConfirmedEvent.animalConfirmedNow;
import static pl.devcezz.shelter.commons.commands.Result.Rejection;
import static pl.devcezz.shelter.commons.commands.Result.Success;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Slf4j
public class Catalogue {

    private final CatalogueRepository repository;
    private final DomainEvents publisher;

    public Try<Result> confirmAnimal(AnimalId animalId) {
        return Try.of(() -> repository.findBy(animalId)
                .map(Animal::confirm)
                .map(this::updateAndPublishConfirmationEvent)
                .map(animal -> Success)
                .getOrElse(Rejection)
        ).onFailure(ex -> log.error("failed to remove animal", ex));
    }

    public Try<Result> addAnimal(AnimalId animalId, Name name, Age age, Species species, Gender gender) {
        return Try.of(() -> Option.of(Animal.create(animalId, name, age, species, gender))
                .map(repository::save)
                .map(animal -> Success)
                .getOrElse(Rejection)
        ).onFailure(ex -> log.error("failed to add new animal", ex));
    }

    public Try<Result> updateAnimal(AnimalId animalId, Name name, Age age, Species species, Gender gender) {
        return Try.of(() -> repository.findBy(animalId)
                .map(animal -> animal.updateFields(name, age, species, gender))
                .map(repository::update)
                .map(animal -> Success)
                .getOrElse(Rejection)
        ).onFailure(ex -> log.error("failed to update animal", ex));
    }

    public Try<Result> deleteAnimal(AnimalId animalId) {
        return Try.of(() -> repository.findBy(animalId)
                .map(Animal::delete)
                .map(repository::delete)
                .map(animal -> Success)
                .getOrElse(Rejection)
        ).onFailure(ex -> log.error("failed to remove animal", ex));
    }

    private Animal updateAndPublishConfirmationEvent(Animal animal) {
        repository.update(animal);
        publisher.publish(animalConfirmedNow(animal.getAnimalId()));
        return animal;
    }

}
