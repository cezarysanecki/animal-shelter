package pl.devcezz.shelter.adoption.proposal.application;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal;
import pl.devcezz.shelter.adoption.proposal.model.Proposal;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;
import pl.devcezz.shelter.commons.aggregates.Version;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAlreadyConfirmed.proposalAlreadyConfirmedNow;
import static pl.devcezz.shelter.catalogue.AnimalEvent.AnimalCreatedEvent;
import static pl.devcezz.shelter.catalogue.AnimalEvent.AnimalDeletedEvent;

@RequiredArgsConstructor
public class AnimalOperationsEventsHandler {

    private final Proposals proposalRepository;
    private final ApplicationEventPublisher publisher;

    @EventListener
    public void handle(AnimalCreatedEvent event) {
        saveProposal(new PendingProposal(
                ProposalId.of(event.getAnimalId()),
                Version.zero()));
    }

    @EventListener
    public void handle(AnimalDeletedEvent event) {
        proposalRepository.findBy(ProposalId.of(event.getAnimalId()))
                .map(this::handleDeletion)
                .map(this::saveProposal);
    }

    private Proposal handleDeletion(Proposal proposal) {
        return Match(proposal).of(
                Case($(instanceOf(PendingProposal.class)), PendingProposal::delete),
                Case($(), () -> proposalIsAlreadyProcessed(proposal))
        );
    }

    private Proposal proposalIsAlreadyProcessed(Proposal proposal) {
        publisher.publishEvent(proposalAlreadyConfirmedNow(proposal.getProposalId()));
        return proposal;
    }

    private Proposal saveProposal(Proposal proposal) {
        proposalRepository.save(proposal);
        return proposal;
    }
}
