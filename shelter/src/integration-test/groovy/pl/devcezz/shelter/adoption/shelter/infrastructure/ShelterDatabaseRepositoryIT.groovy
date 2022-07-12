package pl.devcezz.shelter.adoption.shelter.infrastructure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.adoption.shelter.ShelterTestContext
import pl.devcezz.shelter.adoption.shelter.model.Shelter
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted.proposalAcceptedNow
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled.proposalCanceledNow

@SpringBootTest(classes = ShelterTestContext.class)
class ShelterDatabaseRepositoryIT extends Specification {

    @Autowired
    ShelterDatabaseRepository shelterEntityRepository

    def 'persistence in real database should work for accepted proposal'() {
        given: "Prepare any proposal id."
            ProposalId proposalId = anyProposalId()
        when: "Accept proposal."
            shelterEntityRepository.publish(proposalAcceptedNow(proposalId))
        then: "There is one accepted proposal."
            shelterShouldBeFoundInDatabaseWithOneProposal()
        when: "Cancel proposal."
            shelterEntityRepository.publish(proposalCanceledNow(proposalId))
        then: "There is none accepted proposals."
            shelterShouldBeFoundInDatabaseWithZeroProposals()
    }

    void shelterShouldBeFoundInDatabaseWithOneProposal() {
        Shelter shelter = loadShelter()
        assert shelter.numberOfProposals() == 1
    }

    void shelterShouldBeFoundInDatabaseWithZeroProposals() {
        Shelter shelter = loadShelter()
        assert shelter.numberOfProposals() == 0

    }

    Shelter loadShelter() {
        return shelterEntityRepository.prepareShelter()
    }
}