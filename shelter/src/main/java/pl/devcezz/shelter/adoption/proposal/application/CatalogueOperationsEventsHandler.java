package pl.devcezz.shelter.adoption.proposal.application;

import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.adoption.proposal.model.Proposal;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;
import pl.devcezz.shelter.commons.events.handler.EventsListener;
import pl.devcezz.shelter.commons.infrastructure.AdoptionTransactional;

import static pl.devcezz.shelter.adoption.proposal.model.PendingProposal.createNewPendingProposal;
import static pl.devcezz.shelter.catalogue.CatalogueEvent.AnimalConfirmedEvent;

@RequiredArgsConstructor
public class CatalogueOperationsEventsHandler {

    private final Proposals proposalRepository;

    @EventsListener
    @AdoptionTransactional
    public void handle(AnimalConfirmedEvent event) {
        saveProposal(
                createNewPendingProposal(ProposalId.of(event.getAnimalId())));
    }

    private Proposal saveProposal(Proposal proposal) {
        proposalRepository.save(proposal);
        return proposal;
    }
}
