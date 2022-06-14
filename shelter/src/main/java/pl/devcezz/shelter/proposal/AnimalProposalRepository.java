package pl.devcezz.shelter.proposal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

interface AnimalProposalRepository extends JpaRepository<AnimalProposal, UUID> {

    @Query("UPDATE AnimalProposal ap SET ap.status = 'DELETED'")
    @Modifying
    void deleteAnimalProposalByAnimalProposalId(AnimalProposalId animalProposalId);
}
