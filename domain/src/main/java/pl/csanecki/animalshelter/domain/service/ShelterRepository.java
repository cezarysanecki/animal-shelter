package pl.csanecki.animalshelter.domain.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;
import pl.csanecki.animalshelter.domain.command.AddAnimalCommand;

public interface ShelterRepository {

    long registerAnimal(AddAnimalCommand command);

    Option<AnimalDetails> getAnimalDetails(long animalId);

    List<AnimalShortInfo> getAnimalsInfo();

    void updateAdoptedAtToNow(long animalId);
}
