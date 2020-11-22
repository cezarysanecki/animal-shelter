package pl.csanecki.animalshelter.controller;

import io.vavr.control.Option;
import pl.csanecki.animalshelter.dto.AnimalCreated;
import pl.csanecki.animalshelter.dto.AnimalDetails;
import pl.csanecki.animalshelter.dto.AnimalRequest;

public interface AnimalService {
    Option<AnimalDetails> accept(AnimalRequest animal);

    Option<AnimalDetails> getAnimalBy(int id);
}
