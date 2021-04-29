package pl.csanecki.animalshelter.domain.command;

import pl.csanecki.animalshelter.domain.model.AnimalKind;

public class AddAnimalCommand {

    private final String animalName;
    private final AnimalKind animalKind;
    private final int animalAge;

    public AddAnimalCommand(final String animalName, final AnimalKind animalKind, final int animalAge) {
        this.animalName = animalName;
        this.animalKind = animalKind;
        this.animalAge = animalAge;
    }

    public String getAnimalName() {
        return animalName;
    }

    public AnimalKind getAnimalKind() {
        return animalKind;
    }

    public int getAnimalAge() {
        return animalAge;
    }
}
