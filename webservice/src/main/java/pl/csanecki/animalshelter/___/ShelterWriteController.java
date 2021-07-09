package pl.csanecki.animalshelter.___;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devcezz.cqrs.command.CommandsBus;

import javax.validation.constraints.NotBlank;

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
}

class AddSpeciesRequest {

    @NotBlank
    final String species;

    AddSpeciesRequest(final String species) {
        this.species = species;
    }
}