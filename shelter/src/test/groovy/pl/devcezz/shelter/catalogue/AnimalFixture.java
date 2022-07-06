package pl.devcezz.shelter.catalogue;

import java.util.UUID;

class AnimalFixture {

    static final AnimalId NON_PRESENT_ANIMAL_ID = AnimalId.of(UUID.randomUUID());

    static final Animal DOG = Animal.ofNew(UUID.randomUUID(), "Azor", 5, "Dog", "male");
}
