package pl.csanecki.animalshelter.webservice.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.vavr.collection.List;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.csanecki.animalshelter.domain.animal.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.animal.model.AnimalAge;
import pl.csanecki.animalshelter.domain.animal.model.AnimalKind;
import pl.csanecki.animalshelter.domain.animal.model.AnimalName;
import pl.csanecki.animalshelter.webservice.dto.AnimalDetails;

import java.util.Collection;

@RestController
@RequestMapping("/animals")
public class AnimalRestController {

    private final ShelterService shelterService;

    @Autowired
    public AnimalRestController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PostMapping
    public ResponseEntity<AnimalDetails> acceptIntoShelter(@RequestBody AddAnimalRequest addAnimalRequest) {
        Try<AnimalDetails> result = shelterService.accept(
                new AddAnimalCommand(
                        AnimalName.of(addAnimalRequest.getName()),
                        AnimalKind.of(addAnimalRequest.getKind()),
                        AnimalAge.of(addAnimalRequest.getAge())
                )
        );

        return result
                .map(animal -> ResponseEntity.status(HttpStatus.CREATED).body(animal))
                .getOrElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDetails> getAnimalDetails(@PathVariable int id) {
        Option<AnimalDetails> animalDetails = shelterService.getAnimalBy(id);

        return animalDetails.map(ResponseEntity::ok)
                .getOrElse(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Collection<AnimalDetails>> getAnimals() {
        List<AnimalDetails> allAnimals = shelterService.getAllAnimals();

        return ResponseEntity.ok(allAnimals.asJava());
    }
}

@Value
@AllArgsConstructor(onConstructor_ = { @JsonCreator })
class AddAnimalRequest {

    String name;
    String kind;
    int age;
}