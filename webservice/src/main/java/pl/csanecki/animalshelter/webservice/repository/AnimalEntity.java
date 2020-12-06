package pl.csanecki.animalshelter.webservice.repository;

import lombok.Data;
import pl.csanecki.animalshelter.domain.animal.model.AnimalAge;
import pl.csanecki.animalshelter.domain.animal.model.AnimalId;
import pl.csanecki.animalshelter.domain.animal.model.AnimalKind;
import pl.csanecki.animalshelter.domain.animal.model.AnimalName;
import pl.csanecki.animalshelter.domain.service.entity.AnimalData;
import pl.csanecki.animalshelter.domain.service.entity.AnimalInformation;

import java.time.Instant;

@Data
public class AnimalEntity {

    int id;
    String name;
    String kind;
    int age;
    Instant admittedAt;
    Instant adoptedAt;

    AnimalData toAnimalData() {
        return new AnimalData(new AnimalId(id), new AnimalInformation(AnimalName.of(name), AnimalKind.of(kind), AnimalAge.of(age)), admittedAt, adoptedAt);
    }
}
