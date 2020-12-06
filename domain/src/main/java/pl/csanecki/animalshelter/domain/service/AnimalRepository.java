package pl.csanecki.animalshelter.domain.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.csanecki.animalshelter.domain.animal.model.AnimalId;
import pl.csanecki.animalshelter.domain.service.entity.AnimalData;
import pl.csanecki.animalshelter.domain.service.entity.AnimalInformation;

public interface AnimalRepository {
    Option<AnimalData> save(AnimalInformation animalInformation);

    Option<AnimalData> findAnimalBy(AnimalId id);

    List<AnimalData> findAll();
}
