package pl.csanecki.animalshelter.webservice.repository;

import lombok.Data;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;
import pl.csanecki.animalshelter.domain.model.AnimalAge;
import pl.csanecki.animalshelter.domain.model.AnimalId;
import pl.csanecki.animalshelter.domain.model.AnimalKind;
import pl.csanecki.animalshelter.domain.model.AnimalName;

import java.time.Instant;

@Data
public class AnimalEntity {

    long id;
    String name;
    String kind;
    int age;
    Instant admittedAt;
    Instant adoptedAt;

    AnimalDetails toAnimalDetails() {
        return new AnimalDetails(AnimalId.of(id), AnimalName.of(name), AnimalKind.of(kind), AnimalAge.of(age), admittedAt, adoptedAt);
    }

    AnimalShortInfo toAnimalShortInfo() {
        return new AnimalShortInfo(AnimalId.of(id), AnimalName.of(name), AnimalKind.of(kind), AnimalAge.of(age), adoptedAt == null);
    }
}
