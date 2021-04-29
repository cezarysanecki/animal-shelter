package pl.csanecki.animalshelter.domain.service;

import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;
import pl.csanecki.animalshelter.domain.command.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.command.Result;

import java.util.List;

@Slf4j
public class ShelterService {

    private final ShelterRepository shelterRepository;

    public ShelterService(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    public long acceptIntoShelter(AddAnimalCommand command) {
        return Try.of(() -> shelterRepository.registerAnimal(command))
                .getOrElseThrow(() -> new IllegalArgumentException("Cannot accept animal into shelter"));
    }

    public AnimalDetails getAnimalDetails(long animalId) {
        return shelterRepository.getAnimalDetails(animalId)
                .getOrElseThrow(() -> new IllegalArgumentException("Cannot find details of animal with id: " + animalId));
    }

    public List<AnimalShortInfo> getAnimalsInfo() {
        return shelterRepository.getAnimalsInfo().asJava();
    }

    public Try<Result> adoptAnimal(long animalId) {
        return Try.of(() -> {
            shelterRepository.updateAdoptedAtToNow(animalId);
            return Result.SUCCESS;
        }).onFailure(e -> log.error("Failed to adopt animal", e));
    }
}
