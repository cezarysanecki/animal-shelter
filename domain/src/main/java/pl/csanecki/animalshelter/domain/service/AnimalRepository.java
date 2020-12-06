package pl.csanecki.animalshelter.domain.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.csanecki.animalshelter.domain.service.entity.AnimalData;
import pl.csanecki.animalshelter.domain.service.entity.AnimalDescription;
import pl.csanecki.animalshelter.domain.service.entity.AnimalId;

public interface AnimalRepository {
    Option<AnimalData> save(AnimalDescription animalDescription);

    Option<AnimalData> findAnimalBy(AnimalId id);

    List<AnimalData> findAll();
}
