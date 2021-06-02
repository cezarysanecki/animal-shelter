package pl.csanecki.animalshelter.webservice.repository;

import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;
import pl.csanecki.animalshelter.domain.model.AnimalKind;

import java.time.Instant;
import java.util.UUID;

public class AnimalEntity {

    long id;
    String name;
    String kind;
    int age;
    Instant admittedAt;
    Instant adoptedAt;
    UUID uuid;

    public AnimalEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(final String kind) {
        this.kind = kind;
    }

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public Instant getAdmittedAt() {
        return admittedAt;
    }

    public void setAdmittedAt(final Instant admittedAt) {
        this.admittedAt = admittedAt;
    }

    public Instant getAdoptedAt() {
        return adoptedAt;
    }

    public void setAdoptedAt(final Instant adoptedAt) {
        this.adoptedAt = adoptedAt;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    AnimalDetails toAnimalDetails() {
        return new AnimalDetails(id, name, AnimalKind.findAnimalKind(kind), age, admittedAt, adoptedAt);
    }

    AnimalShortInfo toAnimalShortInfo() {
        return new AnimalShortInfo(id, name, AnimalKind.findAnimalKind(kind), age, adoptedAt == null);
    }
}
