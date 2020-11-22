package pl.csanecki.animalshelter.service;

import io.vavr.control.Option;
import pl.csanecki.animalshelter.dto.AnimalDetails;
import pl.csanecki.animalshelter.dto.AnimalRequest;

public interface AnimalRepository {
    Option<AnimalDetails> save(AnimalRequest animal);

    Option<AnimalDetails> findAnimalBy(int id);
}
