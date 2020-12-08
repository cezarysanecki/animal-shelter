package pl.csanecki.animalshelter.domain.command;

import lombok.Value;
import pl.csanecki.animalshelter.domain.model.AnimalAge;
import pl.csanecki.animalshelter.domain.model.AnimalKind;
import pl.csanecki.animalshelter.domain.model.AnimalName;

@Value
public class AddAnimalCommand {

    AnimalName animalName;
    AnimalKind animalKind;
    AnimalAge animalAge;

    public String getAnimalName() {
        return animalName.getAnimalName();
    }

    public String getAnimalKind() {
        return animalKind.getAnimalKind();
    }

    public int getAnimalAge() {
        return animalAge.getAnimalAge();
    }
}
