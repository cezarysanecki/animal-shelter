package pl.devcezz.animalshelter.shelter;

import pl.devcezz.animalshelter.shelter.command.EditAnimalCommand;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AvailableAnimal;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AdoptedAnimal;
import pl.devcezz.animalshelter.shelter.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.shelter.exception.NotFoundAnimalInShelterException;
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
                Case($(instanceOf(AdoptedAnimal.class)), this::raiseAnimalAlreadyAdopted),
                Case($(instanceOf(AvailableAnimal.class)), () -> edit(editAnimal))
        );
    }

    private Animal raiseAnimalAlreadyAdopted(AdoptedAnimal animal) {
        throw new AnimalAlreadyAdoptedException("cannot edit animal which is already adopted");
    }

    private Animal edit(Animal animal) {
        animals.update(animal);
        return animal;
    }
}
