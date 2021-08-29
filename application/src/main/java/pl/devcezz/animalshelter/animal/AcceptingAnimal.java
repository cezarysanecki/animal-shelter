package pl.devcezz.animalshelter.animal;

import io.vavr.API;
import pl.devcezz.animalshelter.animal.command.AcceptAnimalCommand;
import pl.devcezz.animalshelter.animal.model.Animal;
import pl.devcezz.animalshelter.commons.exception.AcceptingAnimalRejectedException;
import pl.devcezz.animalshelter.commons.Result;
import pl.devcezz.animalshelter.animal.event.AnimalEvent.AcceptingAnimalFailed;
import pl.devcezz.animalshelter.animal.event.AnimalEvent.AcceptingAnimalWarned;
import pl.devcezz.animalshelter.animal.event.AnimalEvent.AcceptingAnimalSucceeded;
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
        return new Result.Rejection(event.getReason());
    }

    private Result saveAndPublishEvent(AcceptingAnimalWarned event, Animal animal) {
        animals.save(animal);
        animals.publish(event);
        return new Result.Success();
    }

    private Result saveAndPublishEvent(AcceptingAnimalSucceeded event, Animal animal) {
        animals.save(animal);
        animals.publish(event);
        return new Result.Success();
    }
}
