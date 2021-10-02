package pl.devcezz.animalshelter.shelter;

import pl.devcezz.animalshelter.shelter.command.DeleteAnimalCommand;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AvailableAnimal;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AdoptedAnimal;
import pl.devcezz.animalshelter.shelter.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.shelter.exception.NotFoundAnimalInShelterException;
import pl.devcezz.animalshelter.shelter.vo.AnimalId;
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
                Case($(instanceOf(AdoptedAnimal.class)), this::raiseAnimalAlreadyAdopted),
                Case($(instanceOf(AvailableAnimal.class)), this::delete)
        );
    }

    private ShelterAnimal raiseAnimalAlreadyAdopted(AdoptedAnimal animal) {
        throw new AnimalAlreadyAdoptedException("cannot delete animal which is already adopted");
    }

    private ShelterAnimal delete(AvailableAnimal animal) {
        animals.delete(animal.animalId());
        return animal;
    }
}
