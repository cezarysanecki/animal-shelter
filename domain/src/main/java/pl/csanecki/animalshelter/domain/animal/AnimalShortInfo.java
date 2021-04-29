package pl.csanecki.animalshelter.domain.animal;

import lombok.Value;
import pl.csanecki.animalshelter.domain.model.AnimalKind;

@Value
public class AnimalShortInfo {

    long id;
    String name;
    AnimalKind kind;
    int age;
    boolean inShelter;

    String getKind() {
        return kind.name();
    }
}
