package pl.csanecki.animalshelter.controller;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.csanecki.animalshelter.dto.AnimalDetails;
import pl.csanecki.animalshelter.dto.AnimalRequest;

@RestController
@RequestMapping("/animals")
public class AnimalRestController {

    private final AnimalService animalService;

    @Autowired
    public AnimalRestController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    public ResponseEntity<AnimalDetails> acceptIntoShelter(@RequestBody AnimalRequest animalRequest) {
        Option<AnimalDetails> createdAnimalDetails = animalService.accept(animalRequest);

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
    public ResponseEntity<List<AnimalDetails>> getAnimals() {
        animalService.getAllAnimals();

        return null;
    }
}
