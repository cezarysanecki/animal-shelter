package pl.csanecki.animalshelter.domain.service;

import pl.csanecki.animalshelter.domain.command.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.model.AnimalId;

public interface ShelterRepository {

    AnimalId registerAnimal(AddAnimalCommand command);
}
