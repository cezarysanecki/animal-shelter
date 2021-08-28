package pl.devcezz.animalshelter.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devcezz.animalshelter.animal.validation.ShelterSpecies;
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

    @Autowired
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

    @PostMapping("/animals/adopt")
    ResponseEntity<Void> acceptAnimal(@RequestBody @Valid AdoptAnimalRequest request) {
        commandsBus.send(new AdoptAnimalCommand(UUID.fromString(request.animalId())));
        return ResponseEntity.ok().build();
    }
}

record AcceptAnimalRequest(
        @NotBlank @Size(min=2, max=11) String name,
        @NotNull @PositiveOrZero @Max(30) Integer age,
        @NotBlank @ShelterSpecies String species) {}

record AdoptAnimalRequest(@NotBlank String animalId) {}