package pl.csanecki.animalshelter.___.species;

import pl.csanecki.animalshelter.___.species.AddSpeciesCommand;
import pl.csanecki.animalshelter.___.species.Species;
import pl.csanecki.animalshelter.___.species.SpeciesRepository;
import pl.devcezz.cqrs.command.CommandHandler;

class AddingSpecies implements CommandHandler<AddSpeciesCommand> {

    private final SpeciesRepository speciesRepository;

    AddingSpecies(final SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public void handle(final AddSpeciesCommand command) {
        Species species = new Species(command.getSpecies());

        if (speciesRepository.contains(species)) {
            throw new IllegalArgumentException("Species " + species.getValue() + " already exists");
        }

        speciesRepository.save(species);
    }
}
