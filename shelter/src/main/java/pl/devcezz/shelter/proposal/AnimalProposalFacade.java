package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.devcezz.shelter.proposal.exception.AnimalProposalNotFound;

@Transactional("proposalTransactionManager")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AnimalProposalFacade {

    private final AnimalProposalRepository animalProposalRepository;

    void acceptProposal(AnimalProposalId animalProposalId) {
        AnimalProposal animalProposal = animalProposalRepository.findByAnimalProposalId(animalProposalId)
                .orElseThrow(() -> new AnimalProposalNotFound(animalProposalId.getValue()));

        animalProposal.accept();
    }

    void declineProposal(AnimalProposalId animalProposalId) {
        AnimalProposal animalProposal = animalProposalRepository.findByAnimalProposalId(animalProposalId)
                .orElseThrow(() -> new AnimalProposalNotFound(animalProposalId.getValue()));

        animalProposal.decline();
    }
}
