package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.proposal.exception.AnimalProposalNotFound;
import pl.devcezz.shelter.shared.infrastructure.ProposalTransaction;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AnimalProposalFacade {

    private final AnimalProposalRepository animalProposalRepository;

    @ProposalTransaction
    public void acceptProposal(AnimalProposalId animalProposalId) {
        AnimalProposal animalProposal = animalProposalRepository.findByAnimalProposalId(animalProposalId)
                .orElseThrow(() -> new AnimalProposalNotFound(animalProposalId.getValue()));

        animalProposal.accept();
    }

    @ProposalTransaction
    public void declineProposal(AnimalProposalId animalProposalId) {
        AnimalProposal animalProposal = animalProposalRepository.findByAnimalProposalId(animalProposalId)
                .orElseThrow(() -> new AnimalProposalNotFound(animalProposalId.getValue()));

        animalProposal.decline();
    }
}
