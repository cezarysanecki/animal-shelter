package pl.csanecki.animalshelter.webservice.controller;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.csanecki.animalshelter.webservice.dto.AnimalDetails;
import pl.csanecki.animalshelter.webservice.dto.AnimalRequest;

public interface AnimalService {
    Option<AnimalDetails> accept(AnimalRequest animal);

    Option<AnimalDetails> getAnimalBy(int id);

    List<AnimalDetails> getAllAnimals();
}
