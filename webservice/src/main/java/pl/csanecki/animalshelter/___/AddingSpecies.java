package pl.csanecki.animalshelter.___;

import pl.devcezz.cqrs.command.CommandHandler;

class AddingSpecies implements CommandHandler<AddSpeciesCommand> {

    private final ShelterRepository shelterRepository;

    AddingSpecies(final ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Override
    public void handle(final AddSpeciesCommand command) {
        Species species = Species.of(command.getSpecies());
    }
}
