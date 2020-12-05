package pl.csanecki.animalshelter.webservice.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.csanecki.animalshelter.webservice.controller.AnimalService;
import pl.csanecki.animalshelter.webservice.dto.AnimalDetails;
import pl.csanecki.animalshelter.webservice.dto.AnimalRequest;

public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public Option<AnimalDetails> accept(AnimalRequest animal) {
        return animalRepository.save(animal);
    }

    @Override
    public Option<AnimalDetails> getAnimalBy(int id) {
        return animalRepository.findAnimalBy(id);
    }

    @Override
    public List<AnimalDetails> getAllAnimals() {
        return animalRepository.findAll();
    }
}
