package pl.csanecki.animalshelter.domain.service;

import io.vavr.collection.List;
import io.vavr.control.Option;
import pl.csanecki.animalshelter.domain.animal.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.service.entity.AnimalData;
import pl.csanecki.animalshelter.domain.service.entity.AnimalDescription;
import pl.csanecki.animalshelter.domain.service.entity.AnimalId;
import pl.csanecki.animalshelter.domain.validation.FailedValidationResult;
import pl.csanecki.animalshelter.domain.validation.SucceededValidationResult;
import pl.csanecki.animalshelter.domain.validation.ValidationResult;
import pl.csanecki.animalshelter.domain.validation.Validator;

import java.util.Set;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

public class ShelterService {

    private final AnimalRepository animalRepository;
    private final Validator validator;

    public ShelterService(AnimalRepository animalRepository, Validator validator) {
        this.animalRepository = animalRepository;
        this.validator = validator;
    }

    public Option<AnimalData> accept(AddAnimalCommand command) {
        ValidationResult validationResult = validator.validate(Set.of(
                command.getAnimalName(),
                command.getAnimalKind(),
                command.getAnimalAge()
        ));

        Match(validationResult).of(
                Case($(instanceOf(SucceededValidationResult.class)), save(command)),
                Case($(instanceOf(FailedValidationResult.class)), save(command))
        );

        return Option.none();
    }

    private Option<AnimalData> save(AddAnimalCommand command) {
        return animalRepository.save(AnimalDescription.of(command.getAnimalName(), command.getAnimalKind(), command.getAnimalAge()));
    }

    public Option<AnimalData> getAnimalBy(long id) {
        return animalRepository.findAnimalBy(new AnimalId(id));
    }

    public List<AnimalData> getAllAnimals() {
        return animalRepository.findAll();
    }
}
