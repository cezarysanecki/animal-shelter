package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.devcezz.shelter.proposal.exception.ProposalNotFoundException;
import pl.devcezz.shelter.shared.event.ProposalDecidedEvent;
import pl.devcezz.shelter.shared.infrastructure.ProposalTransaction;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ProposalFacade {

    private final ProposalRepository proposalRepository;
    private final ApplicationEventPublisher eventPublisher;

    @ProposalTransaction
    public void acceptProposal(UUID subjectId) {
        Proposal proposal = proposalRepository.findLatestProposalFor(
                        SubjectId.of(subjectId))
                .orElseThrow(() -> new ProposalNotFoundException(subjectId));

        Proposal acceptedProposal = proposal.accept();

        proposalRepository.save(acceptedProposal);

        eventPublisher.publishEvent(new ProposalDecidedEvent(
                acceptedProposal.getSubjectId().getValue()));
    }

    @ProposalTransaction
    public void declineProposal(UUID subjectId) {
        Proposal proposal = proposalRepository.findLatestProposalFor(
                        SubjectId.of(subjectId))
                .orElseThrow(() -> new ProposalNotFoundException(subjectId));

        Proposal declinedProposal = proposal.decline();

        proposalRepository.save(declinedProposal);

        eventPublisher.publishEvent(new ProposalDecidedEvent(
                declinedProposal.getSubjectId().getValue()));
    }
}
