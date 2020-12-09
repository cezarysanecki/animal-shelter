package pl.csanecki.animalshelter.domain.service;

import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.command.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.model.AnimalId;

public class ShelterService {

    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public AnimalId acceptIntoShelter(AddAnimalCommand command) {
        return shelterRepository.registerAnimal(command);
    }

    public AnimalDetails getAnimalDetails(AnimalId animalId) {
        return shelterRepository.getAnimalDetails(animalId)
                .getOrElseThrow(() -> new IllegalArgumentException("Cannot find details of animal with id: " + animalId.getAnimalId()));
    }
}
