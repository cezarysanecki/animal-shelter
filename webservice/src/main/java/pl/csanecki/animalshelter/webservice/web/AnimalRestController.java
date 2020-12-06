package pl.csanecki.animalshelter.webservice.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.csanecki.animalshelter.domain.animal.AddAnimalCommand;
import pl.csanecki.animalshelter.domain.animal.validation.AnimalAge;
import pl.csanecki.animalshelter.domain.animal.validation.AnimalKind;
import pl.csanecki.animalshelter.domain.animal.validation.AnimalName;
import pl.csanecki.animalshelter.domain.service.ShelterService;
import pl.csanecki.animalshelter.domain.service.entity.AnimalData;

import java.time.Instant;
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
        Option<AnimalData> result = shelterService.accept(
                new AddAnimalCommand(
                        new AnimalName(addAnimalRequest.getName()),
                        new AnimalKind(addAnimalRequest.getKind()),
                        new AnimalAge(addAnimalRequest.getAge())
                )
        );

        return result
                .map(animal -> ResponseEntity.status(HttpStatus.CREATED).body(new AnimalDetails(animal)))
                .getOrElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDetails> getAnimalDetails(@PathVariable int id) {
        Option<AnimalData> result = shelterService.getAnimalBy(id);

        return result.map(animal -> ResponseEntity.ok().body(new AnimalDetails(animal)))
                .getOrElse(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Collection<AnimalDetails>> getAnimals() {
        List<AnimalData> allAnimals = shelterService.getAllAnimals();

        return ResponseEntity.ok(allAnimals.map(AnimalDetails::new).asJava());
    }
}

@Value
@AllArgsConstructor(onConstructor_ = { @JsonCreator })
class AddAnimalRequest {

    String name;
    String kind;
    int age;
}

@Value
class AnimalDetails {

    long id;
    String name;
    String kind;
    int age;
    Instant admittedAt;
    Instant adoptedAt;

    AnimalDetails(AnimalData animalData) {
        this.id = animalData.id.getAnimalId();
        this.name = animalData.animalDescription.name.name;
        this.kind = animalData.animalDescription.kind.kind;
        this.age = animalData.animalDescription.age.age;
        this.admittedAt = animalData.admittedAt;
        this.adoptedAt = animalData.adoptedAt;
    }
}