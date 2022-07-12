package pl.devcezz.shelter.adoption.shelter.model

import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.shelter
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.shelterWithAcceptedProposal

class ShelterCancelingProposalSpec extends Specification {

    def 'shelter should be able to cancel accepted proposal'() {
        given: "Prepare any proposal id."
            ProposalId proposalId = anyProposalId()
        and: "Prepare shelter with specified proposal."
            Shelter shelter = shelterWithAcceptedProposal(proposalId)
        when: "Cancel proposal."
            def cancelProposal = shelter.cancel(proposalId)
        then: "Operation is successful."
            cancelProposal.isRight()
            cancelProposal.get().with {
                assert it.proposalId == proposalId.value
            }
    }

    def 'shelter cannot cancel a proposal which does not exist'() {
        given: "Prepare any proposal id."
            ProposalId proposalId = anyProposalId()
        and: "Prepare shelter without proposals."
            Shelter shelter = shelter()
        when: "Cancel proposal."
            def cancelProposal = shelter.cancel(proposalId)
        then: "Operation has failed."
            cancelProposal.isLeft()
    }
}