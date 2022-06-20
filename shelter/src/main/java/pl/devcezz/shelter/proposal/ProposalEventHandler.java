package pl.devcezz.shelter.proposal;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import pl.devcezz.shelter.shared.event.AnimalCreatedEvent;
import pl.devcezz.shelter.shared.event.AnimalDeletedEvent;
import pl.devcezz.shelter.shared.infrastructure.ProposalTransaction;

@RequiredArgsConstructor
@ProposalTransaction
class ProposalEventHandler {

    private final ProposalRepository proposalRepository;

    @EventListener
    public void handleCreatedAnimal(AnimalCreatedEvent event) {
        Proposal proposal = Proposal.newOne(
                SubjectId.of(event.getAnimalId()));
        proposalRepository.save(proposal);
    }

    @EventListener
    public void handleDeletedAnimal(AnimalDeletedEvent event) {
        proposalRepository.declineProposalFor(
                SubjectId.of(event.getAnimalId()));
    }
}
