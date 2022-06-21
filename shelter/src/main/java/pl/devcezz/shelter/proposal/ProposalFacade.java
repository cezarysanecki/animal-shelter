package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.devcezz.shelter.shared.event.ProposalDecidedEvent;
import pl.devcezz.shelter.shared.infrastructure.ProposalTransaction;

import java.util.UUID;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static pl.devcezz.shelter.proposal.exception.ProposalIllegalStateException.exceptionCannotAccept;
import static pl.devcezz.shelter.proposal.exception.ProposalIllegalStateException.exceptionCannotDecline;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
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
                Case($(instanceOf(PendingProposal.class)), PendingProposal::accept),
                Case($(), () -> {
                    throw exceptionCannotAccept(proposal.getProposalId());
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
                    throw exceptionCannotDecline(proposal.getProposalId());
                })
        );
    }

    private Proposal saveBook(Proposal proposal) {
        proposalRepository.save(proposal);
        return proposal;
    }
}
