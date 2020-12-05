package pl.csanecki.animalshelter.domain.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import pl.csanecki.animalshelter.domain.animal.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.service.entity.AnimalEntity;

public class ShelterService {

    private final AnimalRepository animalRepository;

    public ShelterService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public Option<AnimalEntity> accept(AddAnimalCommand command) {
        return animalRepository.save(new AnimalEntity(
                command.getAnimalName().getName(),
                command.getAnimalKind().getKind().name(),
                command.getAnimalAge().getAge()
        ));
    }

    public Option<AnimalEntity> getAnimalBy(int id) {
        return animalRepository.findAnimalBy(id);
    }

    public List<AnimalEntity> getAllAnimals() {
        return animalRepository.findAll();
    }
}
