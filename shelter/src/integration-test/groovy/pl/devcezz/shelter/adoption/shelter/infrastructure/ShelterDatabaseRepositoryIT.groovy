package pl.devcezz.shelter.adoption.shelter.infrastructure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.devcezz.shelter.adoption.AdoptionTestContext
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.adoption.shelter.model.Shelter
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted.proposalAcceptedNow
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled.proposalCanceledNow

@SpringBootTest(classes = AdoptionTestContext.class)
class ShelterDatabaseRepositoryIT extends Specification {

    @Autowired
    ShelterDatabaseRepository shelterEntityRepository

    def 'persistence in real database should work for accepted proposal'() {
        given:
            ProposalId proposalId = anyProposalId()
        when:
            shelterEntityRepository.publish(proposalAcceptedNow(proposalId))
        then:
            shelterShouldBeFoundInDatabaseWithOneProposal()
        when:
            shelterEntityRepository.publish(proposalCanceledNow(proposalId))
        then:
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