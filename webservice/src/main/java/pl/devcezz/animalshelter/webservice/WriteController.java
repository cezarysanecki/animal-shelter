package pl.devcezz.animalshelter.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.devcezz.cqrs.command.CommandsBus;

import java.util.UUID;

@RestController
@RequestMapping("/shelter/animals")
class WriteController {

    private final CommandsBus commandsBus;

    @Autowired
    WriteController(CommandsBus commandsBus) {
        this.commandsBus = commandsBus;
    }

    @PostMapping("/{uuid}/adopt")
    ResponseEntity<Void> adoptAnimal(@PathVariable UUID uuid) {
        return null;
    }
}
