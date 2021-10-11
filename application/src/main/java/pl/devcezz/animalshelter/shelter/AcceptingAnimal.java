package pl.devcezz.animalshelter.shelter;

import io.vavr.API;
import pl.devcezz.animalshelter.commons.Result;
import pl.devcezz.animalshelter.shelter.command.AcceptAnimalCommand;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AcceptingAnimalFailed;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AcceptingAnimalSucceeded;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AcceptingAnimalWarned;
import pl.devcezz.animalshelter.shelter.exception.AcceptingAnimalRejectedException;
import pl.devcezz.cqrs.command.CommandHandler;

import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static pl.devcezz.animalshelter.commons.Result.rejection;
import static pl.devcezz.animalshelter.commons.Result.success;

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
                command.animalAge()
        );

        Shelter shelter = shelterFactory.create();
        Result result = Match(shelter.accept(animal)).of(
                API.Case(API.$(instanceOf(AcceptingAnimalFailed.class)), this::publishEvent),
                API.Case(API.$(instanceOf(AcceptingAnimalWarned.class)), event -> saveAndPublishEvent(event, animal)),
                API.Case(API.$(instanceOf(AcceptingAnimalSucceeded.class)), event -> saveAndPublishEvent(event, animal)));

        if (result instanceof Result.Rejection rejection) {
            throw new AcceptingAnimalRejectedException(rejection.getReason());
        }
    }

    private Result publishEvent(AcceptingAnimalFailed event) {
        animals.publish(event);
        return rejection(event.reason());
    }

    private Result saveAndPublishEvent(AcceptingAnimalWarned event, Animal animal) {
        animals.save(animal);
        animals.publish(event);
        return success();
    }

    private Result saveAndPublishEvent(AcceptingAnimalSucceeded event, Animal animal) {
        animals.save(animal);
        animals.publish(event);
        return success();
    }
}
