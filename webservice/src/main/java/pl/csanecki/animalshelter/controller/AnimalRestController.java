package pl.csanecki.animalshelter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.csanecki.animalshelter.dto.AnimalCreated;
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
    public ResponseEntity<AnimalCreated> acceptIntoShelter(@RequestBody AnimalRequest animal) {
        animalService.accept(animal);

        throw new UnsupportedOperationException();
    }

    @GetMapping("/:id")
    public ResponseEntity<AnimalDetails> getAnimalDetails(@PathVariable int id) {
        animalService.getAnimalBy(id);

        throw new UnsupportedOperationException();
    }
}
