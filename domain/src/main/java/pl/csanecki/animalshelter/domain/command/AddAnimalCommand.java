package pl.csanecki.animalshelter.domain.command;

import lombok.Value;
import pl.csanecki.animalshelter.domain.model.AnimalKind;

@Value
public class AddAnimalCommand {

    String animalName;
    AnimalKind animalKind;
    int animalAge;
}
