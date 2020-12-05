package pl.csanecki.animalshelter.domain.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import pl.csanecki.animalshelter.domain.service.entity.AnimalEntity;

public interface AnimalRepository {
    Option<AnimalEntity> save(AnimalEntity animal);

    Option<AnimalEntity> findAnimalBy(int id);

    List<AnimalEntity> findAll();
}
