package pl.csanecki.animalshelter.___.animal;

import pl.csanecki.animalshelter.___.Result;
import pl.csanecki.animalshelter.___.animal.AnimalEvent.AcceptingAnimalFailed;
import pl.csanecki.animalshelter.___.animal.AnimalEvent.AcceptingAnimalSucceeded;
import pl.csanecki.animalshelter.___.animal.AnimalEvent.AcceptingAnimalWarned;
import pl.devcezz.cqrs.command.CommandHandler;
import pl.devcezz.cqrs.event.EventsBus;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static pl.csanecki.animalshelter.___.Result.Rejection;
import static pl.csanecki.animalshelter.___.Result.Success;

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
                Case($(instanceOf(AcceptingAnimalFailed.class)), this::publishEvent),
                Case($(instanceOf(AcceptingAnimalWarned.class)), event -> saveAndPublishEvent(event, animal)),
                Case($(instanceOf(AcceptingAnimalSucceeded.class)), event -> saveAndPublishEvent(event, animal)));

        if (result instanceof Rejection rejection) {
            throw new IllegalArgumentException(rejection.getReason());
        }
    }

    Result publishEvent(AcceptingAnimalFailed event) {
        eventsBus.publish(event);
        return new Rejection(event.getReason());
    }

    Result saveAndPublishEvent(AcceptingAnimalWarned event, Animal animal) {
        shelterRepository.save(animal);
        eventsBus.publish(event);
        return new Success();
    }

    Result saveAndPublishEvent(AcceptingAnimalSucceeded event, Animal animal) {
        shelterRepository.save(animal);
        eventsBus.publish(event);
        return new Success();
    }
}
