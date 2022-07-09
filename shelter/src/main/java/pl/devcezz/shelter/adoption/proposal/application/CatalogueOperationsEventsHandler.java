package pl.devcezz.shelter.adoption.proposal.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import pl.devcezz.shelter.adoption.proposal.model.Proposal;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;

import static pl.devcezz.shelter.adoption.proposal.model.PendingProposal.createNewPendingProposal;
import static pl.devcezz.shelter.catalogue.CatalogueEvent.AnimalConfirmedEvent;

@RequiredArgsConstructor
public class CatalogueOperationsEventsHandler {

    private final Proposals proposalRepository;

    @EventListener
    public void handle(AnimalConfirmedEvent event) {
        saveProposal(
                createNewPendingProposal(ProposalId.of(event.getAnimalId())));
    }

    private Proposal saveProposal(Proposal proposal) {
        proposalRepository.save(proposal);
        return proposal;
    }
}
