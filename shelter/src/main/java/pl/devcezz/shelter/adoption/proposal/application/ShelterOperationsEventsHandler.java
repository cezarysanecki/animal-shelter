package pl.devcezz.shelter.adoption.proposal.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import pl.devcezz.shelter.adoption.proposal.model.AcceptedProposal;
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal;
import pl.devcezz.shelter.adoption.proposal.model.Proposal;
import pl.devcezz.shelter.adoption.proposal.model.ProposalEvent;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled;
import pl.devcezz.shelter.commons.events.DomainEvents;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAlreadyProcessed.proposalAlreadyProcessedNow;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted;

@RequiredArgsConstructor
public class ShelterOperationsEventsHandler {

    private final Proposals proposalRepository;
    private final DomainEvents publisher;

    @EventListener
    public void handle(ProposalAccepted event) {
        proposalRepository.findBy(ProposalId.of(event.getProposalId()))
                .map(this::handleProposalAccepted)
                .map(this::saveProposal);
    }

    @EventListener
    public void handle(ProposalCanceled event) {
        proposalRepository.findBy(ProposalId.of(event.getProposalId()))
                .map(this::handleProposalCanceled)
                .map(this::saveProposal);
    }

    private Proposal handleProposalAccepted(Proposal proposal) {
        return Match(proposal).of(
                Case($(instanceOf(PendingProposal.class)), PendingProposal::accept),
                Case($(), () -> proposalIsAlreadyProcessed(proposal))
        );
    }

    private Proposal handleProposalCanceled(Proposal proposal) {
        return Match(proposal).of(
                Case($(instanceOf(AcceptedProposal.class)), AcceptedProposal::cancel),
                Case($(), () -> proposal)
        );
    }

    private Proposal proposalIsAlreadyProcessed(Proposal proposal) {
        publisher.publish(proposalAlreadyProcessedNow(proposal.getProposalId()));
        return proposal;
    }

    private Proposal saveProposal(Proposal proposal) {
        proposalRepository.save(proposal);
        return proposal;
    }
}
