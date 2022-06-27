package pl.devcezz.shelter.catalogue;

import io.vavr.control.Try;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devcezz.shelter.commons.commands.Result;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/shelter/catalogue")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CatalogueController {

    private final Catalogue catalogue;

    @PostMapping
    ResponseEntity<UUID> createAnimal(@RequestBody AcceptAnimalRequest request) {
        UUID animalId = UUID.randomUUID();

        Try<Result> result = catalogue.addNewAnimal(
                animalId,
                request.name(),
                request.age(),
                request.species(),
                request.gender()
        );
        return result
                .map(success -> ResponseEntity.ok(animalId))
                .getOrElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping
    ResponseEntity<Void> updateAnimal(@RequestBody EditAnimalRequest request) {
        Try<Result> result = catalogue.updateExistingAnimal(
                request.animalId(),
                request.name(),
                request.age(),
                request.species(),
                request.gender()
        );
        return result
                .map(success -> ResponseEntity.ok().<Void>build())
                .getOrElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
    }

    @DeleteMapping
    ResponseEntity<Void> deleteAnimal(@RequestBody String animalId) {
        Try<Result> result = catalogue.removeExistingAnimal(UUID.fromString(animalId));
        return result
                .map(success -> ResponseEntity.ok().<Void>build())
                .getOrElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
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