package pl.devcezz.shelter.proposal;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import pl.devcezz.shelter.shared.Version;
import pl.devcezz.shelter.shared.event.AnimalCreatedEvent;
import pl.devcezz.shelter.shared.event.AnimalDeletedEvent;
import pl.devcezz.shelter.shared.infrastructure.ProposalTransaction;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static pl.devcezz.shelter.proposal.exception.ProposalIllegalStateException.exceptionCannotDelete;

@RequiredArgsConstructor
@ProposalTransaction
class ProposalEventHandler {

    private final ProposalRepository proposalRepository;

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
                Case($(), () -> {
                    throw exceptionCannotDelete(proposal.getProposalId());
                })
        );
    }

    private Proposal saveProposal(Proposal proposal) {
        proposalRepository.save(proposal);
        return proposal;
    }
}
