package pl.csanecki.animalshelter.domain.animal;

import lombok.NonNull;
import lombok.Value;
import pl.csanecki.animalshelter.domain.animal.validation.AnimalAge;
import pl.csanecki.animalshelter.domain.animal.validation.AnimalKind;
import pl.csanecki.animalshelter.domain.animal.validation.AnimalName;

@Value
public class AddAnimalCommand {

    @NonNull AnimalName animalName;
    @NonNull AnimalKind animalKind;
    @NonNull AnimalAge animalAge;
}
