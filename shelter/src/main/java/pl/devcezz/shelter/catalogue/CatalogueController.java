package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.UUID;

@RestController
@RequestMapping("/shelter/catalogue")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CatalogueController {

    private final AnimalService animalService;

    @PostMapping
    ResponseEntity<UUID> createAnimal(@RequestBody AcceptAnimalRequest request) {
        UUID animalId = UUID.randomUUID();

        animalService.save(Animal.ofNew(
                AnimalId.of(animalId),
                request.name(),
                request.age(),
                request.species(),
                request.gender()
        ));

        return ResponseEntity.ok(animalId);
    }

    @PutMapping
    ResponseEntity<Void> updateAnimal(@RequestBody EditAnimalRequest request) {
        animalService.update(Animal.ofNew(
                AnimalId.of(request.animalId()),
                request.name(),
                request.age(),
                request.species(),
                request.gender()
        ));

        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    ResponseEntity<Void> deleteAnimal(@RequestBody String animalId) {
        animalService.delete(AnimalId.of(UUID.fromString(animalId)));
        return ResponseEntity.ok().build();
    }
}

record AcceptAnimalRequest(
        @NotBlank @Size(min = 2, max = 11) String name,
        @NotNull @PositiveOrZero @Max(30) Integer age,
        @NotBlank @Size(min = 2) String species,
        @NotBlank String gender) {
}

record EditAnimalRequest(
        @NotNull UUID animalId,
        @NotBlank @Size(min = 2, max = 11) String name,
        @NotNull @PositiveOrZero @Max(30) Integer age,
        @NotBlank String species,
        @NotBlank String gender) {
}