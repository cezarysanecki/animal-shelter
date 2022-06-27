package pl.devcezz.shelter.adoption.shelter.model

import pl.devcezz.shelter.adoption.proposal.model.PendingProposal
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptingFailed
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.aPendingProposal
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.shelterWithProposals

class ShelterAcceptingProposalSpec extends Specification {

    def 'shelter should be able to accept up to 10 proposals'() {
        given:
            PendingProposal pendingProposal = aPendingProposal()
        and:
            Shelter shelter = shelterWithProposals(proposals)
        when:
            def acceptProposal = shelter.accept(pendingProposal)
        then:
            acceptProposal.isRight()
            acceptProposal.get().with {
                ProposalAccepted proposalAccepted = it.proposalAccepted
                assert proposalAccepted.proposalId == pendingProposal.getProposalId().value
            }
        where:
            proposals << (0..9)
    }

    def 'shelter cannot accept more than 10 proposals'() {
        given:
            PendingProposal pendingProposal = aPendingProposal()
        and:
            Shelter shelter = shelterWithProposals(proposals)
        when:
            def acceptProposal = shelter.accept(pendingProposal)
        then:
            acceptProposal.isLeft()
            ProposalAcceptingFailed e = acceptProposal.getLeft()
            e.getReason().contains("no space left for animals in shelter")
        where:
            proposals << [10, 11, 200]
    }
}