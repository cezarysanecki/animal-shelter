package pl.csanecki.animalshelter.domain.service.entity;

import pl.csanecki.animalshelter.domain.animal.model.AnimalAge;
import pl.csanecki.animalshelter.domain.animal.model.AnimalKind;
import pl.csanecki.animalshelter.domain.animal.model.AnimalName;

public class AnimalInformation {

    public final AnimalName name;
    public final AnimalKind kind;
    public final AnimalAge age;

    public AnimalInformation(AnimalName name, AnimalKind kind, AnimalAge age) {
        this.name = name;
        this.kind = kind;
        this.age = age;
    }
}
