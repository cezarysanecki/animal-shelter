package pl.csanecki.animalshelter.webservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.csanecki.animalshelter.domain.animal.AnimalDetails;
import pl.csanecki.animalshelter.domain.animal.AnimalShortInfo;
import pl.csanecki.animalshelter.domain.service.ShelterService;

import java.util.List;

@RestController
@RequestMapping("/shelter/animals")
public class ShelterController {

    private final ShelterService shelterService;

    @Autowired
    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDetails> getAnimalDetails(@PathVariable long id) {
        AnimalDetails animalDetails = shelterService.getAnimalDetails(id);

        return ResponseEntity.ok(animalDetails);
    }

    @GetMapping
    public ResponseEntity<List<AnimalShortInfo>> getAnimals() {
        List<AnimalShortInfo> animals = shelterService.getAnimalsInfo();

        return ResponseEntity.ok(animals);
    }
}