package pl.devcezz.shelter.proposal;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import pl.devcezz.shelter.shared.event.AnimalCreatedEvent;
import pl.devcezz.shelter.shared.event.AnimalDeletedEvent;

@RequiredArgsConstructor
@Transactional("proposalTransactionManager")
class ProposalEventHandler {

    private final AnimalProposalRepository animalProposalRepository;

    @EventListener
    public void handleCreatedAnimal(AnimalCreatedEvent event) {
        AnimalProposal animalProposal = AnimalProposal.newOne(
                AnimalProposalId.of(event.getAnimalId()));
        animalProposalRepository.save(animalProposal);
    }

    @EventListener
    public void handleDeletedAnimal(AnimalDeletedEvent event) {
        animalProposalRepository.deleteAnimalProposalByAnimalProposalId(
                AnimalProposalId.of(event.getAnimalId()));
    }
}
