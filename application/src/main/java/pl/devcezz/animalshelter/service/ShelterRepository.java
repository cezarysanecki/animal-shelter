package pl.devcezz.animalshelter.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.devcezz.animalshelter.animal.AnimalDetails;
import pl.devcezz.animalshelter.animal.AnimalShortInfo;

public interface ShelterRepository {

    Option<AnimalDetails> getAnimalDetails(long animalId);

    List<AnimalShortInfo> getAnimalsInfo();
}
