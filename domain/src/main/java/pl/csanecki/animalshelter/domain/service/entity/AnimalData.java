package pl.csanecki.animalshelter.domain.service.entity;

import pl.csanecki.animalshelter.domain.animal.model.AnimalId;

import java.time.Instant;

public class AnimalData {

    public final AnimalId id;
    public final AnimalInformation animalInformation;
    public final Instant admittedAt;
    public final Instant adoptedAt;

    public AnimalData(AnimalId id, AnimalInformation animalInformation, Instant admittedAt, Instant adoptedAt) {
        this.id = id;
        this.animalInformation = animalInformation;
        this.admittedAt = admittedAt;
        this.adoptedAt = adoptedAt;
    }
}
