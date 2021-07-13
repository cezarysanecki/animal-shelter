package pl.csanecki.animalshelter.___;

import pl.devcezz.cqrs.event.Event;

public class Shelter {

    private final int capacity;
    private final int safeThreshold;

    public Shelter(final int capacity, final int safeThreshold) {
        this.capacity = capacity;
        this.safeThreshold = safeThreshold;
    }

    Event accept(Animal animal, AnimalsInShelter currentAnimals) {
        if (capacity < currentAnimals.count()) {
            return new AddingAnimalRejected();
        } else if (safeThreshold <= currentAnimals.count()) {
            return new AddingAnimalWarned();
        }

        return new AddingAnimalSucceeded();
    }
}

class AddingAnimalRejected implements Event {}

class AddingAnimalWarned implements Event {}

class AddingAnimalSucceeded implements Event {}