package pl.csanecki.animalshelter.webservice.write.add;

import pl.csanecki.animalshelter.webservice.write.ShelterWriteRepository;
import pl.devcezz.cqrs.command.CommandHandler;

public class AddAnimalCommandHandler implements CommandHandler<AddAnimalCommand> {

    private final ShelterWriteRepository repository;

    public AddAnimalCommandHandler(final ShelterWriteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(final AddAnimalCommand command) {
        repository.registerAnimal(command);
    }
}
