package pl.devcezz.shelter.catalogue;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import pl.devcezz.shelter.catalogue.exception.AnimalNotFoundException;
import pl.devcezz.shelter.shared.event.ProposalDecidedEvent;
import pl.devcezz.shelter.shared.infrastructure.CatalogueTransaction;

@RequiredArgsConstructor
@CatalogueTransaction
class AnimalEventHandler {

    private final AnimalRepository animalRepository;

    @EventListener
    public void handleAnimalProposalDecided(ProposalDecidedEvent event) {
        Animal animal = animalRepository.findByAnimalId(AnimalId.of(event.getSubjectId()))
                .orElseThrow(() -> new AnimalNotFoundException(event.getSubjectId()));

        animal.register();
    }
}
