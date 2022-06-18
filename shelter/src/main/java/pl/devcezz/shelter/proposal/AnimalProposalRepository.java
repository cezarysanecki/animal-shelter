package pl.devcezz.shelter.proposal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

interface AnimalProposalRepository extends JpaRepository<AnimalProposal, UUID> {

    @Query("UPDATE AnimalProposal ap SET ap.status = 'DELETED' WHERE ap.animalProposalId = ?1")
    @Modifying
    void declineAnimalProposalFor(AnimalProposalId animalProposalId);

    Optional<AnimalProposal> findFirstByAnimalProposalIdOrderByCreationTimestampDesc(AnimalProposalId animalProposalId);

    default Optional<AnimalProposal> findLatestAnimalProposalFor(AnimalProposalId animalProposalId) {
        return findFirstByAnimalProposalIdOrderByCreationTimestampDesc(animalProposalId);
    }
}
