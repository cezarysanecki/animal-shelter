package pl.devcezz.shelter.adoption.shelter.model

import pl.devcezz.shelter.adoption.proposal.model.AcceptedProposal
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.*

class ShelterCancelingProposalSpec extends Specification {

    def 'shelter should be able to cancel accepted proposal'() {
        given:
            AcceptedProposal acceptedProposal = anAcceptedProposal()
        and:
            Shelter shelter = shelterWithProposal(acceptedProposal)
        when:
            def cancelProposal = shelter.cancel(acceptedProposal)
        then:
            cancelProposal.isRight()
            cancelProposal.get().with {
                assert it.proposalId == acceptedProposal.proposalId.value
            }
    }

    def 'shelter cannot cancel a proposal which does not exist'() {
        given:
            AcceptedProposal acceptedProposal = anAcceptedProposal()
        and:
            Shelter shelter = shelter()
        when:
            def cancelProposal = shelter.cancel(acceptedProposal)
        then:
            cancelProposal.isLeft()
    }
}