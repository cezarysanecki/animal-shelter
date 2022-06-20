package pl.devcezz.shelter.proposal;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import pl.devcezz.shelter.proposal.exception.ProposalNotFoundException;
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
        Proposal proposal = proposalRepository.findLatestProposalFor(SubjectId.of(event.getAnimalId()));
        proposal.delete();
    }
}
