package pl.devcezz.animalshelter.shelter.read;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devcezz.animalshelter.shelter.application.exception.NotFoundAnimalInShelterException;
import pl.devcezz.animalshelter.shelter.read.dto.AnimalInShelterDto;
import pl.devcezz.animalshelter.shelter.read.query.GetAnimalInfoQuery;
import pl.devcezz.animalshelter.shelter.read.query.GetAnimalsInShelterQuery;
import pl.devcezz.animalshelter.shelter.read.query.GetAnimalsQuery;
import pl.devcezz.animalshelter.shelter.read.dto.AnimalDto;
import pl.devcezz.animalshelter.shelter.read.dto.AnimalInfoDto;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/shelter")
class ShelterReadController {

    private final AnimalProjection projection;

    ShelterReadController(final AnimalProjection projection) {
        this.projection = projection;
    }

    @GetMapping("/animals")
    ResponseEntity<List<AnimalDto>> getAllAnimals() {
        return ResponseEntity.ok(projection.handle(new GetAnimalsQuery()).asJava());
    }

    @GetMapping("/animals/in-shelter")
    ResponseEntity<List<AnimalInShelterDto>> getAllAnimalsInShelter() {
        return ResponseEntity.ok(projection.handle(new GetAnimalsInShelterQuery()).asJava());
    }

    @GetMapping("/animals/{animalId}")
    ResponseEntity<AnimalInfoDto> getAnimalInfo(@PathVariable @NotBlank String animalId) {
        return projection.handle(new GetAnimalInfoQuery(UUID.fromString(animalId)))
            .map(ResponseEntity::ok)
            .getOrElseThrow(NotFoundAnimalInShelterException::new);
    }
}