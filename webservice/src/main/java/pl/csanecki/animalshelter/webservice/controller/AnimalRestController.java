package pl.csanecki.animalshelter.webservice.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.vavr.collection.List;
import io.vavr.control.Option;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.csanecki.animalshelter.webservice.dto.AdmittedAnimal;
import pl.csanecki.animalshelter.webservice.dto.AnimalDetails;

import java.util.Collection;

@RestController
@RequestMapping("/animals")
public class AnimalRestController {

    private final AnimalService animalService;

    @Autowired
    public AnimalRestController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    public ResponseEntity<AnimalDetails> acceptIntoShelter(@RequestBody AdmittedAnimal admittedAnimal) {
        Option<AnimalDetails> createdAnimalDetails = animalService.accept(admittedAnimal);

        return createdAnimalDetails.map(animal -> ResponseEntity.status(HttpStatus.CREATED).body(animal))
                .getOrElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDetails> getAnimalDetails(@PathVariable int id) {
        Option<AnimalDetails> animalDetails = animalService.getAnimalBy(id);

        return animalDetails.map(ResponseEntity::ok)
                .getOrElse(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Collection<AnimalDetails>> getAnimals() {
        List<AnimalDetails> allAnimals = animalService.getAllAnimals();

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