package pl.csanecki.animalshelter.___;

import io.vavr.control.Try;
import pl.csanecki.animalshelter.___.animal.AcceptAnimalCommand;
import pl.devcezz.cqrs.command.CommandHandler;
import pl.devcezz.cqrs.event.Event;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class AcceptingAnimal implements CommandHandler<AcceptAnimalCommand> {

    private final ShelterRepository shelterRepository;

    AcceptingAnimal(final ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Override
    public void handle(final AcceptAnimalCommand command) {
        Animal animal = new Animal(
                command.getAnimalId(),
                command.getName(),
                command.getSpecies(),
                command.getAge()
        );

        if (!shelterRepository.contains(animal.getSpecies())) {
            throw new IllegalArgumentException("Cannot accept animal of species: " + animal.getSpecies().getValue());
        }
    }

    private void handleAccepting(final Animal animal) {
        Try.of(() -> {
            AnimalsInShelter currentAnimals = collectCurrentAnimals();
            Shelter shelter = prepareShelter();
            Event result = shelter.accept(animal, currentAnimals);
            return Match(result).of(
                    Case($(instanceOf(AddingAnimalRejected.class)), this::publishEvent),
                    Case($(instanceOf(AddingAnimalWarned.class)), this::saveAndPublishEvent),
                    Case($(instanceOf(AddingAnimalSucceeded.class)), this::saveAndPublishEvent)
            );
        });
    }

    private AnimalsInShelter collectCurrentAnimals() {
        return shelterRepository.queryForAnimalsInShelter();
    }

    private Shelter prepareShelter() {
        return shelterRepository.queryForShelter();
    }

    Event publishEvent(Event event) {
        return event;
    }

    Event saveAndPublishEvent(Event event) {
        return event;
    }

    private void save(final AcceptAnimalCommand command) {
        shelterRepository.save(
                new Animal(
                        command.getAnimalId(),
                        command.getName(),
                        command.getSpecies(),
                        command.getAge()
                )
        );
    }

    // make it to be business app exception (extends this business error class!)
    private static class UnhandledSpeciesException extends RuntimeException {
        private UnhandledSpeciesException(final String message) {
            super(message);
        }
    }
}
