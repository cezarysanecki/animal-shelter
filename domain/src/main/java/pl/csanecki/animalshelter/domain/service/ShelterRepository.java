package pl.csanecki.animalshelter.domain.service;

import io.vavr.control.Option;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.command.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.model.AnimalId;

public interface ShelterRepository {

    AnimalId registerAnimal(AddAnimalCommand command);

    Option<AnimalDetails> getAnimalDetails(AnimalId animalId);
}
