package pl.devcezz.animalshelter.___.animal;

import io.vavr.API;
import pl.devcezz.animalshelter.___.Result;
import pl.devcezz.cqrs.command.CommandHandler;
import pl.devcezz.cqrs.event.EventsBus;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class AcceptingAnimal implements CommandHandler<AcceptAnimalCommand> {

    private final ShelterRepository shelterRepository;
    private final ShelterFactory shelterFactory;
    private final EventsBus eventsBus;

    AcceptingAnimal(
            final ShelterRepository shelterRepository,
            final ShelterFactory shelterFactory,
            final EventsBus eventsBus
    ) {
        this.shelterRepository = shelterRepository;
        this.shelterFactory = shelterFactory;
        this.eventsBus = eventsBus;
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
        eventsBus.publish(event);
        return new Result.Rejection(event.getReason());
    }

    Result saveAndPublishEvent(AnimalEvent.AcceptingAnimalWarned event, Animal animal) {
        shelterRepository.save(animal);
        eventsBus.publish(event);
        return new Result.Success();
    }

    Result saveAndPublishEvent(AnimalEvent.AcceptingAnimalSucceeded event, Animal animal) {
        shelterRepository.save(animal);
        eventsBus.publish(event);
        return new Result.Success();
    }
}
