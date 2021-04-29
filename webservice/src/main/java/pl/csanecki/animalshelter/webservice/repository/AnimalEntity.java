package pl.csanecki.animalshelter.webservice.repository;

import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;
import pl.csanecki.animalshelter.domain.model.AnimalKind;

import java.time.Instant;

public class AnimalEntity {

    long id;
    String name;
    String kind;
    int age;
    Instant admittedAt;
    Instant adoptedAt;

    AnimalDetails toAnimalDetails() {
        return new AnimalDetails(id, name, AnimalKind.findAnimalKind(kind), age, admittedAt, adoptedAt);
    }

    AnimalShortInfo toAnimalShortInfo() {
        return new AnimalShortInfo(id, name, AnimalKind.findAnimalKind(kind), age, adoptedAt == null);
    }
}
