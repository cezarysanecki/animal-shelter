package pl.devcezz.animalshelter.animal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devcezz.animalshelter.animal.validation.ShelterSpecies;
import pl.devcezz.cqrs.command.CommandsBus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
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
    ResponseEntity<Void> acceptAnimal(@RequestBody AcceptAnimalRequest request) {
        UUID animalId = UUID.randomUUID();

        commandsBus.send(
                new AcceptAnimalCommand(
                        animalId,
                        request.name(),
                        request.species(),
                        request.age()
                )
        );

        return ResponseEntity.ok().build();
    }
}

record AcceptAnimalRequest(@NotBlank String name,
                           @ShelterSpecies String species,
                           @PositiveOrZero int age) {}