package pl.devcezz.animalshelter.animal;

import pl.devcezz.animalshelter.commons.exception.NotFoundAnimalInShelterException;
import pl.devcezz.cqrs.command.CommandHandler;

import static pl.devcezz.animalshelter.animal.AnimalEvent.AnimalAdoptionSucceeded.adoptingAnimalSucceeded;

class AdoptingAnimal implements CommandHandler<AdoptAnimalCommand> {

    private final Animals animals;

    AdoptingAnimal(final Animals animals) {
        this.animals = animals;
    }

    @Override
    public void handle(final AdoptAnimalCommand command) {
        AnimalId animalId = new AnimalId(command.animalId());

        animals.findNotAdoptedBy(animalId)
            .peek(animals::adopt)
            .peek(this::publishEvent)
            .getOrElseThrow(NotFoundAnimalInShelterException::new);
    }

    private void publishEvent(ShelterAnimal animal) {
        animals.publish(adoptingAnimalSucceeded(animal.animalId()));
    }
}
