package pl.devcezz.shelter.adoption.proposal.application;

import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.adoption.proposal.model.AcceptedProposal;
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal;
import pl.devcezz.shelter.adoption.proposal.model.Proposal;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled;
import pl.devcezz.shelter.commons.events.DomainEvents;
import pl.devcezz.shelter.commons.events.handler.EventsListener;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAcceptanceConfirmed.proposalAcceptanceConfirmedNow;
import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAcceptanceFailed.proposalAcceptanceFailedNow;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted;

@RequiredArgsConstructor
public class ShelterOperationsEventsHandler {

    private final Proposals proposalRepository;
    private final DomainEvents publisher;

    @EventsListener
    public void handle(ProposalAccepted event) {
        proposalRepository.findBy(ProposalId.of(event.getProposalId()))
                .map(this::handleProposalAccepted)
                .map(this::saveProposal)
                .onEmpty(() -> proposalAcceptanceFailed(ProposalId.of(event.getProposalId())));
    }

    @EventsListener
    public void handle(ProposalCanceled event) {
        proposalRepository.findBy(ProposalId.of(event.getProposalId()))
                .map(this::handleProposalCanceled)
                .map(this::saveProposal);
    }

    private Proposal handleProposalAccepted(Proposal proposal) {
        return Match(proposal).of(
                Case($(instanceOf(PendingProposal.class)), this::proposalAcceptanceAllowed),
                Case($(), () -> proposalAcceptanceFailed(proposal))
        );
    }

    private Proposal handleProposalCanceled(Proposal proposal) {
        return Match(proposal).of(
                Case($(instanceOf(AcceptedProposal.class)), AcceptedProposal::cancel),
                Case($(), () -> proposal)
        );
    }

    private Proposal proposalAcceptanceAllowed(PendingProposal pendingProposal) {
        AcceptedProposal acceptedProposal = pendingProposal.accept();
        publisher.publish(proposalAcceptanceConfirmedNow(acceptedProposal.getProposalId()));
        return acceptedProposal;
    }

    private void proposalAcceptanceFailed(ProposalId proposalId) {
        publisher.publish(proposalAcceptanceFailedNow("proposal not found", proposalId));
    }

    private Proposal proposalAcceptanceFailed(Proposal proposal) {
        publisher.publish(proposalAcceptanceFailedNow("proposal already processed", proposal.getProposalId()));
        return proposal;
    }

    private Proposal saveProposal(Proposal proposal) {
        proposalRepository.save(proposal);
        return proposal;
    }
}
