package pl.devcezz.animalshelter.shelter.application;

import io.vavr.API;
import pl.devcezz.animalshelter.shelter.application.command.DeleteAnimalCommand;
import pl.devcezz.animalshelter.shelter.application.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.shelter.application.exception.NotFoundAnimalInShelterException;
import pl.devcezz.cqrs.command.CommandHandler;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class DeletingAnimal implements CommandHandler<DeleteAnimalCommand> {

    private final Animals animals;

    DeletingAnimal(final Animals animals) {
        this.animals = animals;
    }

    @Override
    public void handle(final DeleteAnimalCommand command) {
        AnimalId animalId = new AnimalId(command.animalId());

        animals.findBy(animalId)
                .peek(this::tryDelete)
                .getOrElseThrow(NotFoundAnimalInShelterException::new);
    }

    private void tryDelete(ShelterAnimal animal) {
        Match(animal).of(
                API.Case(API.$(instanceOf(ShelterAnimal.AdoptedAnimal.class)), this::raiseAnimalAlreadyAdopted),
                API.Case(API.$(instanceOf(ShelterAnimal.AvailableAnimal.class)), this::delete)
        );
    }

    private ShelterAnimal raiseAnimalAlreadyAdopted(ShelterAnimal.AdoptedAnimal animal) {
        throw new AnimalAlreadyAdoptedException("cannot delete animal which is already adopted");
    }

    private ShelterAnimal delete(ShelterAnimal.AvailableAnimal animal) {
        animals.delete(animal.animalId());
        return animal;
    }
}
