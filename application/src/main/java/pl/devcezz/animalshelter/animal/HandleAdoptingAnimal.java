package pl.devcezz.animalshelter.animal;

import pl.devcezz.animalshelter.animal.event.AnimalEvent.AnimalAdoptionSucceeded;
import pl.devcezz.cqrs.event.EventHandler;

class HandleAdoptingAnimal implements EventHandler<AnimalAdoptionSucceeded> {

    @Override
    public void handle(final AnimalAdoptionSucceeded event) {
        System.out.println("Adopted: " + event.getAnimalId().value());
    }
}
