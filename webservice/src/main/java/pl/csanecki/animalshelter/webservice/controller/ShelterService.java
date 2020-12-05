package pl.csanecki.animalshelter.webservice.controller;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import pl.csanecki.animalshelter.domain.animal.AddAnimalCommand;
import pl.csanecki.animalshelter.webservice.dto.AnimalDetails;

public interface ShelterService {
    Try<AnimalDetails> accept(AddAnimalCommand command);

    Option<AnimalDetails> getAnimalBy(int id);

    List<AnimalDetails> getAllAnimals();
}
