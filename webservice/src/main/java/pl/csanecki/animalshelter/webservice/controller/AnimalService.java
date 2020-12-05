package pl.csanecki.animalshelter.webservice.controller;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.csanecki.animalshelter.webservice.dto.AnimalDetails;
import pl.csanecki.animalshelter.webservice.dto.AdmittedAnimal;

public interface AnimalService {
    Option<AnimalDetails> accept(AdmittedAnimal animal);

    Option<AnimalDetails> getAnimalBy(int id);

    List<AnimalDetails> getAllAnimals();
}
