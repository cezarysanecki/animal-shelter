package pl.csanecki.animalshelter.service;

import org.springframework.beans.factory.annotation.Autowired;
import pl.csanecki.animalshelter.controller.AnimalSerivce;

public class AnimalServiceImpl implements AnimalSerivce {

    private AnimalRepository animalRepository;

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }
}
