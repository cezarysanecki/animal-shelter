package pl.devcezz.animalshelter.animal;

import pl.devcezz.animalshelter.animal.command.AdoptAnimalCommand;
import pl.devcezz.animalshelter.animal.model.AdoptedAnimal;
import pl.devcezz.animalshelter.animal.model.AnimalId;
import pl.devcezz.animalshelter.animal.model.AvailableAnimal;
import pl.devcezz.animalshelter.animal.model.ShelterAnimal;
import pl.devcezz.animalshelter.commons.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.commons.exception.NotFoundAnimalInShelterException;
import pl.devcezz.cqrs.command.CommandHandler;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static pl.devcezz.animalshelter.animal.event.AnimalEvent.AnimalAdoptionSucceeded.adoptingAnimalSucceeded;

class AdoptingAnimal implements CommandHandler<AdoptAnimalCommand> {

    private final Animals animals;

    AdoptingAnimal(final Animals animals) {
        this.animals = animals;
    }

    @Override
    public void handle(final AdoptAnimalCommand command) {
        AnimalId animalId = new AnimalId(command.animalId());

        animals.findBy(animalId)
            .peek(this::tryAdopt)
            .getOrElseThrow(NotFoundAnimalInShelterException::new);
    }

    private void tryAdopt(ShelterAnimal animal) {
        Match(animal).of(
                Case($(instanceOf(AdoptedAnimal.class)), this::raiseAnimalAlreadyAdopted),
                Case($(instanceOf(AvailableAnimal.class)), this::adopt)
        );
    }

    private ShelterAnimal raiseAnimalAlreadyAdopted(AdoptedAnimal animal) {
        throw new AnimalAlreadyAdoptedException();
    }

    private ShelterAnimal adopt(AvailableAnimal animal) {
        animals.adopt(animal);
        animals.publish(adoptingAnimalSucceeded(animal.animalId()));
        return animal;
    }
}
