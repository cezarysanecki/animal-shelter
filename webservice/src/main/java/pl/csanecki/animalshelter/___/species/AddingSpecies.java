package pl.csanecki.animalshelter.___.species;

import io.vavr.collection.List;
import pl.devcezz.cqrs.command.CommandHandler;

class AddingSpecies implements CommandHandler<AddSpeciesCommand> {

    private final SpeciesRepository speciesRepository;

    AddingSpecies(final SpeciesRepository speciesRepository) {
        this.speciesRepository = speciesRepository;
    }

    @Override
    public void handle(final AddSpeciesCommand command) {
        Species species = new Species(command.getSpecies());

        List<Species> shelterSpecies = speciesRepository.findAllSpecies();
        if (shelterSpecies.contains(species)) {
            throw new IllegalArgumentException("Species is already acceptable: " + species.getValue());
        }

        speciesRepository.save(species);
    }
}
