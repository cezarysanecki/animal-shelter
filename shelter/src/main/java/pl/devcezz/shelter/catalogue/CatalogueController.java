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
import pl.devcezz.shelter.commons.commands.ResponseUtils;
import pl.devcezz.shelter.commons.commands.Result;
import pl.devcezz.shelter.commons.model.Age;
import pl.devcezz.shelter.commons.model.Gender;
import pl.devcezz.shelter.commons.model.Name;
import pl.devcezz.shelter.commons.model.Species;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.UUID;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static pl.devcezz.shelter.commons.commands.ResponseUtils.resolveResult;

@RestController
@RequestMapping("/shelter/catalogue")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class CatalogueController {

    private final Catalogue catalogue;

    @PostMapping("/confirm")
    ResponseEntity<Void> confirmAnimal(@RequestBody ConfirmAnimalRequest request) {
        Try<Result> result = catalogue.confirmAnimal(
                AnimalId.of(request.animalId()));
        return result
                .map(ResponseUtils::resolveResult)
                .getOrElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
    }

    @PostMapping
    ResponseEntity<UUID> createAnimal(@RequestBody AcceptAnimalRequest request) {
        UUID animalId = UUID.randomUUID();

        Try<Result> result = catalogue.addAnimal(
                AnimalId.of(animalId),
                Name.of(request.name()),
                Age.of(request.age()),
                Species.of(request.species()),
                Gender.of(request.gender())
        );
        return result
                .map(r -> resolveResult(r, animalId))
                .getOrElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
    }

    @PutMapping
    ResponseEntity<Void> updateAnimal(@RequestBody EditAnimalRequest request) {
        Try<Result> result = catalogue.updateAnimal(
                AnimalId.of(request.animalId()),
                Name.of(request.name()),
                Age.of(request.age()),
                Species.of(request.species()),
                Gender.of(request.gender())
        );
        return result
                .map(ResponseUtils::resolveResult)
                .getOrElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
    }

    @DeleteMapping
    ResponseEntity<Void> deleteAnimal(@RequestBody DeleteAnimalRequest request) {
        Try<Result> result = catalogue.deleteAnimal(
                AnimalId.of(request.animalId()));
        return result
                .map(ResponseUtils::resolveResult)
                .getOrElse(ResponseEntity.status(INTERNAL_SERVER_ERROR).build());
    }
}

record ConfirmAnimalRequest(
        @NotNull UUID animalId) {
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

record DeleteAnimalRequest(
        @NotNull UUID animalId) {
}