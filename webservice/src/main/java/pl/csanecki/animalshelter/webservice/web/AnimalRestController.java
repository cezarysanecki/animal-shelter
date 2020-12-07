package pl.csanecki.animalshelter.webservice.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.csanecki.animalshelter.domain.service.ShelterService;

@RestController
@RequestMapping("/animals")
public class AnimalRestController {

    private final ShelterService shelterService;

    @Autowired
    public AnimalRestController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @PostMapping
    public ResponseEntity acceptIntoShelter(@RequestBody AddAnimalRequest addAnimalRequest) {
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
@AllArgsConstructor(onConstructor_ = { @JsonCreator })
class AddAnimalRequest {

    String name;
    String kind;
    int age;
}