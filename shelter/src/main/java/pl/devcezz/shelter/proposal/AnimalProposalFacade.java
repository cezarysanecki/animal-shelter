package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.devcezz.shelter.proposal.exception.AnimalProposalNotFound;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class AnimalProposalFacade {

    private final AnimalProposalRepository animalProposalRepository;

    @Transactional("proposalTransactionManager")
    public void acceptProposal(AnimalProposalId animalProposalId) {
        AnimalProposal animalProposal = animalProposalRepository.findByAnimalProposalId(animalProposalId)
                .orElseThrow(() -> new AnimalProposalNotFound(animalProposalId.getValue()));

        animalProposal.accept();
    }

    @Transactional("proposalTransactionManager")
    public void declineProposal(AnimalProposalId animalProposalId) {
        AnimalProposal animalProposal = animalProposalRepository.findByAnimalProposalId(animalProposalId)
                .orElseThrow(() -> new AnimalProposalNotFound(animalProposalId.getValue()));

        animalProposal.decline();
    }
}
