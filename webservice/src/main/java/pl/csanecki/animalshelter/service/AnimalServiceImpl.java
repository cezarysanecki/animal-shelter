package pl.csanecki.animalshelter.service;

import io.vavr.control.Option;
import pl.csanecki.animalshelter.controller.AnimalService;
import pl.csanecki.animalshelter.dto.AnimalCreated;
import pl.csanecki.animalshelter.dto.AnimalDetails;
import pl.csanecki.animalshelter.dto.AnimalRequest;

public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public AnimalCreated accept(AnimalRequest animal) {
        return animalRepository.save(animal);
    }

    @Override
    public Option<AnimalDetails> getAnimalBy(int id) {
        return animalRepository.findAnimalBy(id);
    }
}
