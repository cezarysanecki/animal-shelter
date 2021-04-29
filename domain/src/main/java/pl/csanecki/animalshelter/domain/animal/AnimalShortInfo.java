package pl.csanecki.animalshelter.domain.animal;

import pl.csanecki.animalshelter.domain.model.AnimalKind;

public class AnimalShortInfo {

    private final long id;
    private final String name;
    private final AnimalKind kind;
    private final int age;
    private final boolean inShelter;

    public AnimalShortInfo(final long id, final String name, final AnimalKind kind, final int age, final boolean inShelter) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.age = age;
        this.inShelter = inShelter;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AnimalKind getKind() {
        return kind;
    }

    public int getAge() {
        return age;
    }

    public boolean isInShelter() {
        return inShelter;
    }
}
