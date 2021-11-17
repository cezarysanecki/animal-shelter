package pl.devcezz.animalshelter.shelter;

import pl.devcezz.animalshelter.shelter.command.AdoptAnimalCommand;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.SuccessfulAnimalAdoption;
import pl.devcezz.animalshelter.shelter.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.shelter.exception.NotFoundAnimalInShelterException;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AvailableAnimal;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AdoptedAnimal;
import pl.devcezz.cqrs.command.CommandHandler;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

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
        throw new AnimalAlreadyAdoptedException("cannot adopt animal which is already adopted");
    }

    private ShelterAnimal adopt(AvailableAnimal animal) {
        animals.adopt(animal);
        animals.publish(adoptionSuccessful(animal));
        return animal;
    }

    private AnimalEvent adoptionSuccessful(final AvailableAnimal animal) {
        return new SuccessfulAnimalAdoption(
                animal.animalId().value(),
                animal.getAnimalInformation().name(),
                animal.getAnimalInformation().age(),
                animal.getAnimalInformation().species()
        );
    }
}
