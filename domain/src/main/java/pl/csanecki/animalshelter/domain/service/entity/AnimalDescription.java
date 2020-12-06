package pl.csanecki.animalshelter.domain.service.entity;


import pl.csanecki.animalshelter.domain.animal.validation.AnimalAge;
import pl.csanecki.animalshelter.domain.animal.validation.AnimalKind;
import pl.csanecki.animalshelter.domain.animal.validation.AnimalName;

public class AnimalDescription {

    public final AnimalName name;
    public final AnimalKind kind;
    public final AnimalAge age;

    private AnimalDescription(AnimalName name, AnimalKind kind, AnimalAge age) {
        this.name = name;
        this.kind = kind;
        this.age = age;
    }

    public static AnimalDescription of(AnimalName name, AnimalKind kind, AnimalAge age) {
        return new AnimalDescription(name, kind, age);
    }
}
