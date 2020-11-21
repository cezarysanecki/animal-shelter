package pl.csanecki.animalshelter.service;

import pl.csanecki.animalshelter.dto.AnimalCreated;
import pl.csanecki.animalshelter.dto.AnimalRequest;

public interface AnimalRepository {
    AnimalCreated save(AnimalRequest animal);
}
