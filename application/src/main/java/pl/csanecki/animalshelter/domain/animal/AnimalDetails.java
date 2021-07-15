package pl.csanecki.animalshelter.domain.animal;

import pl.csanecki.animalshelter.domain.model.AnimalKind;

import java.time.Instant;

public class AnimalDetails {

    private final long id;
    private final String name;
    private final AnimalKind kind;
    private final int age;
    private final Instant admittedAt;
    private final Instant adoptedAt;

    public AnimalDetails(final long id, final String name, final AnimalKind kind, final int age, final Instant admittedAt, final Instant adoptedAt) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.age = age;
        this.admittedAt = admittedAt;
        this.adoptedAt = adoptedAt;
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

    public Instant getAdmittedAt() {
        return admittedAt;
    }

    public Instant getAdoptedAt() {
        return adoptedAt;
    }
}
