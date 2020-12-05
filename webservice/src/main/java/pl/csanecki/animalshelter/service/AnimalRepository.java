package pl.csanecki.animalshelter.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.csanecki.animalshelter.model.AnimalDetails;
import pl.csanecki.animalshelter.model.AnimalRequest;

public interface AnimalRepository {
    Option<AnimalDetails> save(AnimalRequest animal);

    Option<AnimalDetails> findAnimalBy(int id);

    List<AnimalDetails> findAll();
}
