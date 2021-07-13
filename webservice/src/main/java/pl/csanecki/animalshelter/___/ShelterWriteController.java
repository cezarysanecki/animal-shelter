package pl.csanecki.animalshelter.___;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.csanecki.animalshelter.___.animal.AcceptAnimalCommand;
import pl.csanecki.animalshelter.___.species.AddSpeciesCommand;
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

    @PostMapping("/species/add")
    ResponseEntity<Void> addAnimalSpecies(@RequestBody AddSpeciesRequest request) {
        commandsBus.send(
                new AddSpeciesCommand(request.species)
        );

        return ResponseEntity.ok().build();
    }

    @PostMapping("/animals/accept")
    ResponseEntity<Void> acceptAnimal(@RequestBody AcceptAnimalRequest request) {
        UUID animalId = UUID.randomUUID();

        commandsBus.send(
                new AcceptAnimalCommand(
                        animalId,
                        request.name,
                        request.species,
                        request.age
                )
        );

        return ResponseEntity.ok().build();
    }
}

class AddSpeciesRequest {

    @NotBlank
    final String species;

    AddSpeciesRequest(final String species) {
        this.species = species;
    }
}

class AcceptAnimalRequest {

    @NotBlank
    final String name;

    @NotBlank
    final String species;

    @PositiveOrZero
    final int age;

    AcceptAnimalRequest(final String name, final String species, final int age) {
        this.name = name;
        this.species = species;
        this.age = age;
    }
}