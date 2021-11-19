package pl.devcezz.animalshelter.shelter.application;

import io.vavr.API;
import pl.devcezz.animalshelter.shelter.application.command.EditAnimalCommand;
import pl.devcezz.animalshelter.shelter.application.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.shelter.application.exception.NotFoundAnimalInShelterException;
import pl.devcezz.cqrs.command.CommandHandler;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class EditingAnimal implements CommandHandler<EditAnimalCommand> {

    private final Animals animals;

    EditingAnimal(final Animals animals) {
        this.animals = animals;
    }

    @Override
    public void handle(final EditAnimalCommand command) {
        Animal editAnimal = new Animal(
                command.animalId(),
                command.animalName(),
                command.animalSpecies(),
                command.animalAge(),
                command.animalGender());

        animals.findBy(editAnimal.getId())
                .peek(animal -> tryEditAnimal(animal, editAnimal))
                .getOrElseThrow(NotFoundAnimalInShelterException::new);
    }

    private void tryEditAnimal(ShelterAnimal animal, Animal editAnimal) {
        Match(animal).of(
                API.Case(API.$(instanceOf(ShelterAnimal.AdoptedAnimal.class)), this::raiseAnimalAlreadyAdopted),
                API.Case(API.$(instanceOf(ShelterAnimal.AvailableAnimal.class)), () -> edit(editAnimal))
        );
    }

    private Animal raiseAnimalAlreadyAdopted(ShelterAnimal.AdoptedAnimal animal) {
        throw new AnimalAlreadyAdoptedException("cannot edit animal which is already adopted");
    }

    private Animal edit(Animal animal) {
        animals.update(animal);
        return animal;
    }
}
