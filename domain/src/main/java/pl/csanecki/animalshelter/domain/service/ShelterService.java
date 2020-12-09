package pl.csanecki.animalshelter.domain.service;

import io.vavr.control.Try;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.command.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.model.AnimalId;

public class ShelterService {

    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public AnimalId acceptIntoShelter(AddAnimalCommand command) {
        return Try.of(() -> shelterRepository.registerAnimal(command))
                .getOrElseThrow(() -> new IllegalArgumentException("Cannot accept animal into shelter"));
    }

    public AnimalDetails getAnimalDetails(AnimalId animalId) {
        return shelterRepository.getAnimalDetails(animalId)
                .getOrElseThrow(() -> new IllegalArgumentException("Cannot find details of animal with id: " + animalId.getAnimalId()));
    }
}
