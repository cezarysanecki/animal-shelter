package pl.devcezz.shelter.adoption.shelter.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;

import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAlreadyProcessed;

@RequiredArgsConstructor
public class ShelterEventsHandler {

    private final CancelingProposal cancelingProposal;

    @EventListener
    public void handle(ProposalAlreadyProcessed event) {
        cancelingProposal.cancelProposal(cancelProposalCommandFrom(event));
    }

    private CancelProposalCommand cancelProposalCommandFrom(ProposalAlreadyProcessed event) {
        return new CancelProposalCommand(
                ProposalId.of(event.getProposalId()));
    }
}
