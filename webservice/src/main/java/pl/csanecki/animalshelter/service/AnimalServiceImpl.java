package pl.csanecki.animalshelter.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.csanecki.animalshelter.controller.AnimalService;
import pl.csanecki.animalshelter.dto.AnimalCreated;
import pl.csanecki.animalshelter.dto.AnimalRequest;

public class AnimalServiceImpl implements AnimalService {

    private AnimalRepository animalRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public AnimalCreated accept(AnimalRequest animal) {
        return animalRepository.save(animal);
    }
}
