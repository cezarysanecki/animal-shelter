package pl.csanecki.animalshelter.webservice.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.csanecki.animalshelter.webservice.dto.AnimalDetails;
import pl.csanecki.animalshelter.webservice.dto.AdmittedAnimal;

public interface AnimalRepository {
    Option<AnimalDetails> save(AdmittedAnimal animal);

    Option<AnimalDetails> findAnimalBy(int id);

    List<AnimalDetails> findAll();
}
