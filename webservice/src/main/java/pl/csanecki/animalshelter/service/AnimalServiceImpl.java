package pl.csanecki.animalshelter.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.csanecki.animalshelter.controller.AnimalService;

public class AnimalServiceImpl implements AnimalService {

    private AnimalRepository animalRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }
}
