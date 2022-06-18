package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.proposal.exception.AnimalProposalNotFoundException;
import pl.devcezz.shelter.shared.infrastructure.ProposalTransaction;

import java.util.UUID;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AnimalProposalFacade {

    private final AnimalProposalRepository animalProposalRepository;

    @ProposalTransaction
    public void acceptProposal(UUID animalProposalId) {
        AnimalProposal animalProposal = animalProposalRepository.findLatestAnimalProposalBy(
                        AnimalProposalId.of(animalProposalId))
                .orElseThrow(() -> new AnimalProposalNotFoundException(animalProposalId));

        AnimalProposal acceptedAnimalProposal = animalProposal.accept();

        animalProposalRepository.save(acceptedAnimalProposal);
    }

    @ProposalTransaction
    public void declineProposal(UUID animalProposalId) {
        AnimalProposal animalProposal = animalProposalRepository.findLatestAnimalProposalBy(
                        AnimalProposalId.of(animalProposalId))
                .orElseThrow(() -> new AnimalProposalNotFoundException(animalProposalId));

        AnimalProposal declinedAnimalProposal = animalProposal.decline();

        animalProposalRepository.save(declinedAnimalProposal);
    }
}
