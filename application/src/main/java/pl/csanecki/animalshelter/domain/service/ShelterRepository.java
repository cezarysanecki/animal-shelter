package pl.csanecki.animalshelter.domain.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;

public interface ShelterRepository {

    Option<AnimalDetails> getAnimalDetails(long animalId);

    List<AnimalShortInfo> getAnimalsInfo();
}
