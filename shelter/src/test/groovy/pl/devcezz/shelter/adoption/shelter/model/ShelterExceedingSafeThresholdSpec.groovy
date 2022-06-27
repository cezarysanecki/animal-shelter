package pl.devcezz.shelter.adoption.shelter.model

import pl.devcezz.shelter.adoption.proposal.model.PendingProposal
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.SafeThresholdExceeded
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.aPendingProposal
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.shelterWithProposals

class ShelterExceedingSafeThresholdSpec extends Specification {

    def 'should announce that shelter exceeds safe threshold'() {
        given:
            PendingProposal pendingProposal = aPendingProposal()
        and:
            Shelter shelter = shelterWithProposals(proposals)
        when:
            def acceptProposal = shelter.accept(pendingProposal)
        then:
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