package pl.devcezz.animalshelter.shelter;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devcezz.animalshelter.shelter.command.AcceptAnimalCommand;
import pl.devcezz.animalshelter.shelter.command.AdoptAnimalCommand;
import pl.devcezz.animalshelter.shelter.command.DeleteAnimalCommand;
import pl.devcezz.animalshelter.shelter.command.EditAnimalCommand;
import pl.devcezz.common.Gender;
import pl.devcezz.cqrs.command.CommandsBus;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.UUID;

@RestController
@RequestMapping("/shelter")
class ShelterWriteController {

    private final CommandsBus commandsBus;

    ShelterWriteController(CommandsBus commandsBus) {
        this.commandsBus = commandsBus;
    }

    @PostMapping("/animals/accept")
    ResponseEntity<Void> acceptAnimal(@RequestBody @Valid AcceptAnimalRequest request) {
        commandsBus.send(
                new AcceptAnimalCommand(
                        UUID.randomUUID(),
                        request.name(),
                        request.species(),
                        request.age()
                )
        );

        return ResponseEntity.ok().build();
    }

    @PutMapping("/animals")
    ResponseEntity<Void> editAnimal(@RequestBody @Valid EditAnimalRequest request) {
        commandsBus.send(
                new EditAnimalCommand(
                        request.animalId(),
                        request.name(),
                        request.species(),
                        request.age()
                )
        );

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/animals")
    ResponseEntity<Void> deleteAnimal(@RequestBody @NotBlank String animalId) {
        commandsBus.send(new DeleteAnimalCommand(UUID.fromString(animalId)));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/animals/adopt")
    ResponseEntity<Void> adoptAnimal(@RequestBody @NotBlank String animalId) {
        commandsBus.send(new AdoptAnimalCommand(UUID.fromString(animalId)));
        return ResponseEntity.ok().build();
    }
}

record AcceptAnimalRequest(
        @NotBlank @Size(min=2, max=11) String name,
        @NotNull @PositiveOrZero @Max(30) Integer age,
        @NotBlank String species,
        @ShelterGender String gender) {}

record EditAnimalRequest(
        @NotNull UUID animalId,
        @NotBlank @Size(min=2, max=11) String name,
        @NotNull @PositiveOrZero @Max(30) Integer age,
        @NotBlank String species,
        @ShelterGender String gender) {}