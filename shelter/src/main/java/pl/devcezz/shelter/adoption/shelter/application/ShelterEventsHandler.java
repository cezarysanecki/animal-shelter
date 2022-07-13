package pl.devcezz.shelter.adoption.shelter.application;

import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAcceptanceConfirmed;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.shelter.model.Shelters;
import pl.devcezz.shelter.commons.events.handler.EventsListener;
import pl.devcezz.shelter.commons.infrastructure.AdoptionTransactional;

import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAcceptanceFailed;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalConfirmed.proposalConfirmedNow;

@RequiredArgsConstructor
public class ShelterEventsHandler {

    private final CancelingProposal cancelingProposal;
    private final Shelters shelterRepository;

    @EventsListener
    @AdoptionTransactional
    public void handle(ProposalAcceptanceFailed event) {
        cancelingProposal.cancelProposal(cancelProposalCommandFrom(event));
    }

    @EventsListener
    @AdoptionTransactional
    public void handle(ProposalAcceptanceConfirmed event) {
        shelterRepository.publish(proposalConfirmedNow(ProposalId.of(event.getProposalId())));
    }

    private CancelProposalCommand cancelProposalCommandFrom(ProposalAcceptanceFailed event) {
        return new CancelProposalCommand(
                ProposalId.of(event.getProposalId()));
    }
}
