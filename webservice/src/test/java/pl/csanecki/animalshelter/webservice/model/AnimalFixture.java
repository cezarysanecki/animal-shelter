package pl.csanecki.animalshelter.webservice.model;

import io.vavr.collection.List;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;
import pl.csanecki.animalshelter.domain.model.AnimalKind;

import java.time.Instant;

public class AnimalFixture {

    public static long anyAnimalId() {
        return 20;
    }

    public static long animalId(long id) {
        return id;
    }

    public static AnimalDetails animalInShelter(long id) {
        return new AnimalDetails(animalId(id), "Azor", AnimalKind.DOG, 2, Instant.now(), null);
    }

    public static List<AnimalShortInfo> animalsInShelter() {
        return List.of(new AnimalShortInfo(anyAnimalId(), "Azor", AnimalKind.DOG, 2, true));
    }
}
