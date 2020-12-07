package pl.csanecki.animalshelter.webservice.web;

import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.csanecki.animalshelter.domain.service.ShelterService;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/animals")
public class AnimalRestController {

    private final ShelterService shelterService;

    @Autowired
    public AnimalRestController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PostMapping
    public ResponseEntity<Void> acceptIntoShelter(@Valid @RequestBody AddAnimalRequest addAnimalRequest) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity getAnimalDetails(@PathVariable long id) {
        return null;
    }

    @GetMapping
    public ResponseEntity getAnimals() {
        return null;
    }
}

@Value
class AddAnimalRequest {

    @NotEmpty
    @Size(min = 3, max = 25)
    String name;

    String kind;

    @PositiveOrZero
    int age;
}