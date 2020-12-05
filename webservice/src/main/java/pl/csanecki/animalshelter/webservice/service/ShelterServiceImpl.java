package pl.csanecki.animalshelter.webservice.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.csanecki.animalshelter.webservice.controller.ShelterService;
import pl.csanecki.animalshelter.webservice.dto.AnimalDetails;
import pl.csanecki.animalshelter.webservice.dto.AdmittedAnimal;

public class ShelterServiceImpl implements ShelterService {

    private final AnimalRepository animalRepository;

    public ShelterServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public Option<AnimalDetails> accept(AdmittedAnimal animal) {
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
