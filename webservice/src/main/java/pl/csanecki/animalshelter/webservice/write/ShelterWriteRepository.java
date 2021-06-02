package pl.csanecki.animalshelter.webservice.write;

import pl.csanecki.animalshelter.webservice.write.add.AddAnimalCommand;

import java.util.UUID;

public interface ShelterWriteRepository {

    void registerAnimal(AddAnimalCommand command);

    void updateAdoptedAtToNow(UUID uuid);
}
