package pl.devcezz.animalshelter.shelter.application;

import io.vavr.API;
import pl.devcezz.animalshelter.shelter.commons.Result;
import pl.devcezz.animalshelter.shelter.application.command.AcceptAnimalCommand;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent.FailedAnimalAcceptance;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent.SuccessfulAnimalAcceptance;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent.WarnedAnimalAcceptance;
import pl.devcezz.animalshelter.shelter.application.exception.AcceptingAnimalRejectedException;
import pl.devcezz.cqrs.command.CommandHandler;

import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class AcceptingAnimal implements CommandHandler<AcceptAnimalCommand> {

    private final Animals animals;
    private final ShelterFactory shelterFactory;

    AcceptingAnimal(
            final Animals animals,
            final ShelterFactory shelterFactory
    ) {
        this.animals = animals;
        this.shelterFactory = shelterFactory;
    }

    @Override
    public void handle(final AcceptAnimalCommand command) {
        Animal animal = new Animal(
                command.animalId(),
                command.animalName(),
                command.animalSpecies(),
                command.animalAge(),
                command.animalGender());

        Shelter shelter = shelterFactory.create();
        Result result = Match(shelter.accept(animal)).of(
                API.Case(API.$(instanceOf(FailedAnimalAcceptance.class)), this::publishEvent),
                API.Case(API.$(instanceOf(WarnedAnimalAcceptance.class)), event -> saveAndPublishEvent(event, animal)),
                API.Case(API.$(instanceOf(SuccessfulAnimalAcceptance.class)), event -> saveAndPublishEvent(event, animal)));

        if (result instanceof Result.Rejection rejection) {
            throw new AcceptingAnimalRejectedException(rejection.getReason());
        }
    }

    private Result publishEvent(FailedAnimalAcceptance event) {
        animals.publish(event);
        return Result.rejection(event.reason());
    }

    private Result saveAndPublishEvent(WarnedAnimalAcceptance event, Animal animal) {
        animals.save(animal);
        animals.publish(event);
        return Result.success();
    }

    private Result saveAndPublishEvent(SuccessfulAnimalAcceptance event, Animal animal) {
        animals.save(animal);
        animals.publish(event);
        return Result.success();
    }
}
