package pl.devcezz.animalshelter.webservice.web;

import io.vavr.collection.List;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devcezz.animalshelter.animal.AnimalDetails;
import pl.devcezz.animalshelter.animal.AnimalShortInfo;
import pl.devcezz.animalshelter.service.ShelterRepository;

@RestController
@RequestMapping("/shelter/animals")
public class ShelterController {

    private final ShelterRepository shelterRepository;

    @Autowired
    public ShelterController(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDetails> getAnimalDetails(@PathVariable long id) {
        Option<AnimalDetails> animalDetails = shelterRepository.getAnimalDetails(id);

        return ResponseEntity.ok(animalDetails.getOrElseThrow(IllegalArgumentException::new));
    }

    @GetMapping
    public ResponseEntity getAnimals() {
        List<AnimalShortInfo> animalsInfo = shelterRepository.getAnimalsInfo();

        return ResponseEntity.ok(animalsInfo.asJava());
    }
}