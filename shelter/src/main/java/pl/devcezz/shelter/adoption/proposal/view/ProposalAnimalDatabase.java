package pl.devcezz.shelter.adoption.proposal.view;

import org.springframework.data.repository.CrudRepository;

interface ProposalAnimalDatabase extends CrudRepository<ProposalAnimalData, Long> {

}
