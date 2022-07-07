package pl.devcezz.shelter.adoption.shelter.model

import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.shelter
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.shelterWithProposal

class ShelterCancelingProposalSpec extends Specification {

    def 'shelter should be able to cancel accepted proposal'() {
        given:
            ProposalId proposalId = anyProposalId()
        and:
            Shelter shelter = shelterWithProposal(proposalId)
        when:
            def cancelProposal = shelter.cancel(proposalId)
        then:
            cancelProposal.isRight()
            cancelProposal.get().with {
                assert it.proposalId == proposalId.value
            }
    }

    def 'shelter cannot cancel a proposal which does not exist'() {
        given:
            ProposalId proposalId = anyProposalId()
        and:
            Shelter shelter = shelter()
        when:
            def cancelProposal = shelter.cancel(proposalId)
        then:
            cancelProposal.isLeft()
    }
}