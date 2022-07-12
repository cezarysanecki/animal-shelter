package pl.devcezz.shelter.adoption.shelter.model

import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.SafeThresholdExceeded
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.shelterWithAcceptedProposals

class ShelterExceedingSafeThresholdSpec extends Specification {

    def 'should announce that shelter exceeds safe threshold'() {
        given: "Prepare any proposal id."
            ProposalId proposalId = anyProposalId()
        and: "Prepare shelter with some proposals."
            Shelter shelter = shelterWithAcceptedProposals(proposals)
        when: "Accept proposal."
            def acceptProposal = shelter.accept(proposalId)
        then: "Operation is successful but only some space is available."
            acceptProposal.isRight()
            acceptProposal.get().with {
                assert it.safeThresholdExceeded.isDefined()
                SafeThresholdExceeded safeThresholdExceeded = it.safeThresholdExceeded.get()
                assert safeThresholdExceeded.spaceLeft == spaceLeft
            }
        where:
            proposals || spaceLeft
            6         || 3
            7         || 2
            8         || 1
            9         || 0
    }
}