package pl.csanecki.animalshelter.webservice.web;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;
import pl.csanecki.animalshelter.domain.command.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.model.*;
import pl.csanecki.animalshelter.domain.service.ShelterService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequestMapping("/shelter/animals")
public class ShelterController {

    private final ShelterService shelterService;

    @Autowired
    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PostMapping
    public ResponseEntity<Void> acceptIntoShelter(@Valid @RequestBody AddAnimalRequest addAnimalRequest) {
        AnimalId animalId = shelterService.acceptIntoShelter(new AddAnimalCommand(
                AnimalName.of(addAnimalRequest.getName()),
                AnimalKind.of(addAnimalRequest.getKind()),
                AnimalAge.of(addAnimalRequest.getAge())
        ));

        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(animalId.getAnimalId())
                        .toUri()
        ).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDetails> getAnimalDetails(@PathVariable long id) {
        AnimalDetails animalDetails = shelterService.getAnimalDetails(AnimalId.of(id));

        return ResponseEntity.ok(animalDetails);
    }

    @GetMapping
    public ResponseEntity<AnimalsShortInfo> getAnimals() {
        List<AnimalShortInfo> animals = shelterService.getAnimalsInfo();

        return ResponseEntity.ok(new AnimalsShortInfo(animals));
    }
}

@Value
class AddAnimalRequest {

    @NotEmpty
    @Size(min = AnimalName.MIN_LENGTH, max = AnimalName.MAX_LENGTH)
    String name;

    @ValueOfAnimalKind(enumClass = AnimalKindType.class)
    String kind;

    @PositiveOrZero
    int age;
}

@Value
class AnimalsShortInfo {

    List<AnimalShortInfo> animals;
}