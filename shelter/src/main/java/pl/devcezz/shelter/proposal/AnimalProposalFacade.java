package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.devcezz.shelter.proposal.exception.AnimalProposalNotFoundException;
import pl.devcezz.shelter.shared.event.AnimalProposalDecidedEvent;
import pl.devcezz.shelter.shared.infrastructure.ProposalTransaction;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AnimalProposalFacade {

    private final AnimalProposalRepository animalProposalRepository;
    private final ApplicationEventPublisher eventPublisher;

    @ProposalTransaction
    public void acceptProposal(UUID animalProposalId) {
        AnimalProposal animalProposal = animalProposalRepository.findLatestAnimalProposalFor(
                        AnimalProposalId.of(animalProposalId))
                .orElseThrow(() -> new AnimalProposalNotFoundException(animalProposalId));

        AnimalProposal acceptedAnimalProposal = animalProposal.accept();

        animalProposalRepository.save(acceptedAnimalProposal);

        eventPublisher.publishEvent(new AnimalProposalDecidedEvent(
                acceptedAnimalProposal.getAnimalProposalId().getValue()));
    }

    @ProposalTransaction
    public void declineProposal(UUID animalProposalId) {
        AnimalProposal animalProposal = animalProposalRepository.findLatestAnimalProposalFor(
                        AnimalProposalId.of(animalProposalId))
                .orElseThrow(() -> new AnimalProposalNotFoundException(animalProposalId));

        AnimalProposal declinedAnimalProposal = animalProposal.decline();

        animalProposalRepository.save(declinedAnimalProposal);

        eventPublisher.publishEvent(new AnimalProposalDecidedEvent(
                declinedAnimalProposal.getAnimalProposalId().getValue()));
    }
}
