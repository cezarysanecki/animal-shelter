package pl.devcezz.shelter.proposal;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;
import pl.devcezz.shelter.shared.event.AnimalCreatedEvent;
import pl.devcezz.shelter.shared.event.AnimalDeletedEvent;

@RequiredArgsConstructor
class ProposalEventHandler {

    private final AnimalProposalRepository animalProposalRepository;

    @EventListener
    @Transactional("proposalTransactionManager")
    public void handleCreatedAnimal(AnimalCreatedEvent event) {
        AnimalProposal animalProposal = AnimalProposal.newOne(
                AnimalProposalId.of(event.getAnimalId()));
        animalProposalRepository.save(animalProposal);
    }

    @EventListener
    @Transactional("proposalTransactionManager")
    public void handleDeletedAnimal(AnimalDeletedEvent event) {
        animalProposalRepository.deleteByAnimalProposalId(
                AnimalProposalId.of(event.getAnimalId()));
    }
}
