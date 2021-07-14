package pl.csanecki.animalshelter.___.animal;

import pl.csanecki.animalshelter.___.animal.AnimalEvent.AcceptingAnimalFailed;
import pl.csanecki.animalshelter.___.animal.AnimalEvent.AcceptingAnimalSucceeded;
import pl.csanecki.animalshelter.___.animal.AnimalEvent.AcceptingAnimalWarned;
import pl.csanecki.animalshelter.___.species.SpeciesRepository;
import pl.devcezz.cqrs.command.CommandHandler;
import pl.devcezz.cqrs.event.Event;
import pl.devcezz.cqrs.event.EventsBus;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class AcceptingAnimal implements CommandHandler<AcceptAnimalCommand> {

    private final ShelterRepository shelterRepository;
    private final SpeciesRepository speciesRepository;
    private final ShelterFactory shelterFactory;
    private final EventsBus eventsBus;

    AcceptingAnimal(
            final ShelterRepository shelterRepository,
            final SpeciesRepository speciesRepository,
            final ShelterFactory shelterFactory,
            final EventsBus eventsBus
    ) {
        this.shelterRepository = shelterRepository;
        this.speciesRepository = speciesRepository;
        this.shelterFactory = shelterFactory;
        this.eventsBus = eventsBus;
    }

    @Override
    public void handle(final AcceptAnimalCommand command) {
        Animal animal = new Animal(
                command.getAnimalId(),
                command.getName(),
                command.getSpecies(),
                command.getAge()
        );

        if (!speciesRepository.findAllSpecies().contains(animal.getSpecies())) {
            throw new IllegalArgumentException("Cannot accept animal of species: " + animal.getSpecies().getValue());
        }

        handleAccepting(animal);
    }

    private void handleAccepting(final Animal animal) {
        Shelter shelter = shelterFactory.create();
        Event result = shelter.accept(animal);
        Match(result).of(
                Case($(instanceOf(AcceptingAnimalFailed.class)), this::publishEvent),
                Case($(instanceOf(AcceptingAnimalWarned.class)), event -> saveAndPublishEvent(event, animal)),
                Case($(instanceOf(AcceptingAnimalSucceeded.class)), event -> saveAndPublishEvent(event, animal))
        );
    }

    Event publishEvent(Event event) {
        eventsBus.publish(event);
        return event;
    }

    Event saveAndPublishEvent(Event event, Animal animal) {
        shelterRepository.save(animal);
        return publishEvent(event);
    }

    private static class UnhandledSpeciesException extends RuntimeException {
        private UnhandledSpeciesException(final String message) {
            super(message);
        }
    }
}
