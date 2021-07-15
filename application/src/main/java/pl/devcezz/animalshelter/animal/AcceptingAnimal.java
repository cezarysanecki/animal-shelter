package pl.devcezz.animalshelter.animal;

import io.vavr.API;
import pl.devcezz.animalshelter.Result;
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
                command.animalAge(),
                command.animalSpecies()
        );

        Shelter shelter = shelterFactory.create();
        Result result = Match(shelter.accept(animal)).of(
                API.Case(API.$(instanceOf(AnimalEvent.AcceptingAnimalFailed.class)), this::publishEvent),
                API.Case(API.$(instanceOf(AnimalEvent.AcceptingAnimalWarned.class)), event -> saveAndPublishEvent(event, animal)),
                API.Case(API.$(instanceOf(AnimalEvent.AcceptingAnimalSucceeded.class)), event -> saveAndPublishEvent(event, animal)));

        if (result instanceof Result.Rejection rejection) {
            throw new IllegalArgumentException(rejection.getReason());
        }
    }

    Result publishEvent(AnimalEvent.AcceptingAnimalFailed event) {
        animals.publish(event);
        return new Result.Rejection(event.getReason());
    }

    Result saveAndPublishEvent(AnimalEvent.AcceptingAnimalWarned event, Animal animal) {
        animals.save(animal);
        animals.publish(event);
        return new Result.Success();
    }

    Result saveAndPublishEvent(AnimalEvent.AcceptingAnimalSucceeded event, Animal animal) {
        animals.save(animal);
        animals.publish(event);
        return new Result.Success();
    }
}
