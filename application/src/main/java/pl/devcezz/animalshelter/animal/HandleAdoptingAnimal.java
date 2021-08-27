package pl.devcezz.animalshelter.animal;

import pl.devcezz.animalshelter.animal.AnimalEvent.AdoptedAnimalSucceeded;
import pl.devcezz.cqrs.event.EventHandler;

class HandleAdoptingAnimal implements EventHandler<AdoptedAnimalSucceeded> {

    @Override
    public void handle(final AdoptedAnimalSucceeded event) {
        System.out.println("Adopted: " + event.getAnimalId().value());
    }
}
