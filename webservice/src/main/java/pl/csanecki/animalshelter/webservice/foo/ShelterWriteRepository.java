package pl.csanecki.animalshelter.webservice.foo;

import pl.csanecki.animalshelter.webservice.foo.add.AddAnimalCommand;

import java.util.UUID;

public interface ShelterWriteRepository {

    void registerAnimal(AddAnimalCommand command);

    void updateAdoptedAtToNow(UUID uuid);
}
