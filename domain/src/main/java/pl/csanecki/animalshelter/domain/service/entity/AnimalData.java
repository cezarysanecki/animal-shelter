package pl.csanecki.animalshelter.domain.service.entity;

import java.time.Instant;

public class AnimalData {

    public final AnimalId id;
    public final AnimalDescription animalDescription;
    public final Instant admittedAt;
    public final Instant adoptedAt;

    public AnimalData(AnimalId id, AnimalDescription animalDescription, Instant admittedAt, Instant adoptedAt) {
        this.id = id;
        this.animalDescription = animalDescription;
        this.admittedAt = admittedAt;
        this.adoptedAt = adoptedAt;
    }
}
