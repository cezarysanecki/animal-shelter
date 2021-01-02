package pl.csanecki.animalshelter.domain.animal;

import lombok.Value;
import pl.csanecki.animalshelter.domain.model.AnimalAge;
import pl.csanecki.animalshelter.domain.model.AnimalId;
import pl.csanecki.animalshelter.domain.model.AnimalKind;
import pl.csanecki.animalshelter.domain.model.AnimalName;

@Value
public class AnimalShortInfo {

    AnimalId id;
    AnimalName name;
    AnimalKind kind;
    AnimalAge age;
    boolean inShelter;

    public long getId() {
        return id.getAnimalId();
    }

    public String getName() {
        return name.getAnimalName();
    }

    public String getKind() {
        return kind.getAnimalKind();
    }

    public int getAge() {
        return age.getAnimalAge();
    }
}
