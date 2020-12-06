package pl.csanecki.animalshelter.domain.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.csanecki.animalshelter.domain.animal.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.animal.model.AnimalId;
import pl.csanecki.animalshelter.domain.service.entity.AnimalData;
import pl.csanecki.animalshelter.domain.service.entity.AnimalInformation;
import pl.csanecki.animalshelter.domain.validation.Validator;

public class ShelterService {

    private final AnimalRepository animalRepository;
    private final Validator validator;

    public ShelterService(AnimalRepository animalRepository, Validator validator) {
        this.animalRepository = animalRepository;
        this.validator = validator;
    }

    public Option<AnimalData> accept(AddAnimalCommand command) {
        return animalRepository.save(new AnimalInformation(
                command.getAnimalName(),
                command.getAnimalKind(),
                command.getAnimalAge()
        ));
    }

    public Option<AnimalData> getAnimalBy(int id) {
        return animalRepository.findAnimalBy(new AnimalId(id));
    }

    public List<AnimalData> getAllAnimals() {
        return animalRepository.findAll();
    }
}
