package pl.csanecki.animalshelter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.csanecki.animalshelter.dto.AnimalCreated;
import pl.csanecki.animalshelter.dto.AnimalRequest;

@RestController
@RequestMapping("/animals")
public class AnimalRestController {

    private AnimalSerivce animalSerivce;

    @Autowired
    public AnimalRestController(AnimalSerivce animalSerivce) {
        this.animalSerivce = animalSerivce;
    }

    @PostMapping
    public ResponseEntity<AnimalCreated> acceptIntoShelter(AnimalRequest animal) {
        animalSerivce.accept(animal);

        throw new UnsupportedOperationException();
    }
}
