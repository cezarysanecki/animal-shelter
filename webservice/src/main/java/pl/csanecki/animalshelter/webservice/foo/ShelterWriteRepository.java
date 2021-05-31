package pl.csanecki.animalshelter.webservice.foo;

import pl.csanecki.animalshelter.webservice.foo.add.AddAnimalCommand;

public interface ShelterWriteRepository {

    void registerAnimal(AddAnimalCommand command);

    void updateAdoptedAtToNow(long animalId);
}
