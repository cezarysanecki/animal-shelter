package pl.csanecki.animalshelter.webservice.model;

import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.model.AnimalAge;
import pl.csanecki.animalshelter.domain.model.AnimalId;
import pl.csanecki.animalshelter.domain.model.AnimalKind;
import pl.csanecki.animalshelter.domain.model.AnimalName;

import java.time.Instant;

public class AnimalFixture {

    public static AnimalId anyAnimalId() {
        return animalId(20);
    }

    public static AnimalId animalId(long id) {
        return AnimalId.of(id);
    }

    public static AnimalDetails animalInShelter(long id) {
        return new AnimalDetails(animalId(id), AnimalName.of("Azor"), AnimalKind.of("DOG"), AnimalAge.of(2), Instant.now(), null);
    }
}
