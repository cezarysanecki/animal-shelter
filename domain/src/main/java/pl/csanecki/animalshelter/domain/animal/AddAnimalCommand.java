package pl.csanecki.animalshelter.domain.animal;

import lombok.NonNull;
import lombok.Value;
import pl.csanecki.animalshelter.domain.animal.model.AnimalAge;
import pl.csanecki.animalshelter.domain.animal.model.AnimalKind;
import pl.csanecki.animalshelter.domain.animal.model.AnimalName;

@Value
public class AddAnimalCommand {

    @NonNull AnimalName animalName;
    @NonNull AnimalKind animalKind;
    @NonNull AnimalAge animalAge;

}
