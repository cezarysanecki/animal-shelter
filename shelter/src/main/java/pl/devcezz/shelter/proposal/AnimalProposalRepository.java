package pl.devcezz.shelter.proposal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface AnimalProposalRepository extends JpaRepository<AnimalProposal, UUID> {

    void deleteByAnimalProposalId(AnimalProposalId animalProposalId);
}
