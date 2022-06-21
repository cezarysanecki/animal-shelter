package pl.devcezz.shelter.proposal.application;

import io.vavr.API;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.devcezz.shelter.proposal.model.PendingProposal;
import pl.devcezz.shelter.proposal.model.Proposal;
import pl.devcezz.shelter.proposal.model.ProposalId;
import pl.devcezz.shelter.proposal.model.ProposalRepository;
import pl.devcezz.shelter.shared.event.ProposalDecidedEvent;
import pl.devcezz.shelter.shared.infrastructure.ProposalTransaction;

import java.util.UUID;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor
public class ProposalFacade {

    private final ProposalRepository proposalRepository;
    private final ApplicationEventPublisher eventPublisher;

    @ProposalTransaction
    public void acceptProposal(UUID proposalId) {
        proposalRepository.findBy(ProposalId.of(proposalId))
                .map(this::acceptProposal)
                .map(this::saveBook);

        eventPublisher.publishEvent(new ProposalDecidedEvent(proposalId));
    }

    private Proposal acceptProposal(Proposal proposal) {
        return Match(proposal).of(
                API.Case(API.$(instanceOf(PendingProposal.class)), PendingProposal::accept),
                Case($(), () -> {
                    throw new IllegalStateException("Cannot accept proposal: " + proposal.getProposalId());
                })
        );
    }

    @ProposalTransaction
    public void declineProposal(UUID proposalId) {
        proposalRepository.findBy(ProposalId.of(proposalId))
                .map(this::declineProposal)
                .map(this::saveBook);

        eventPublisher.publishEvent(new ProposalDecidedEvent(proposalId));
    }

    private Proposal declineProposal(Proposal proposal) {
        return Match(proposal).of(
                Case($(instanceOf(PendingProposal.class)), PendingProposal::decline),
                Case($(), () -> {
                    throw new IllegalStateException("Cannot decline proposal: " + proposal.getProposalId());
                })
        );
    }

    private Proposal saveBook(Proposal proposal) {
        proposalRepository.save(proposal);
        return proposal;
    }
}
