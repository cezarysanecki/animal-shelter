package pl.devcezz.shelter.catalogue;

import pl.devcezz.shelter.commons.model.Age;
import pl.devcezz.shelter.commons.model.Gender;
import pl.devcezz.shelter.commons.model.Name;
import pl.devcezz.shelter.commons.model.Species;

import java.util.UUID;

class AnimalFixture {

    static final AnimalId NON_PRESENT_ANIMAL_ID = AnimalId.of(UUID.randomUUID());

    static AnimalId anyAnimalId() {
        return AnimalId.of(UUID.randomUUID());
    }

    static Animal dog() {
        return Animal.create(AnimalId.of(UUID.randomUUID()), Name.of("Azor"), Age.of(5), Species.of("Dog"), Gender.Male);
    }
}
