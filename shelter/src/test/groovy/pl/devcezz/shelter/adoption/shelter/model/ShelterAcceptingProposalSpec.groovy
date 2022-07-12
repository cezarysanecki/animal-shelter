package pl.devcezz.shelter.adoption.shelter.model

import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptingFailed
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.*

class ShelterAcceptingProposalSpec extends Specification {

    def 'shelter should be able to accept up to 10 proposals'() {
        given: "Prepare any proposal id."
            ProposalId proposalId = anyProposalId()
        and: "Prepare shelter with some proposals."
            Shelter shelter = shelterWithAcceptedProposals(proposals)
        when: "Accept new proposal."
            def acceptProposal = shelter.accept(proposalId)
        then: "Operation is successful."
            acceptProposal.isRight()
            acceptProposal.get().with {
                ProposalAccepted proposalAccepted = it.proposalAccepted
                assert proposalAccepted.proposalId == proposalId.value
            }
        where:
            proposals << (0..9)
    }

    def 'shelter cannot accept more than 10 proposals'() {
        given: "Prepare any proposal id."
            ProposalId proposalId = anyProposalId()
        and: "Prepare shelter with some proposals."
            Shelter shelter = shelterWithAcceptedProposals(proposals)
        when: "Accept new proposal."
            def acceptProposal = shelter.accept(proposalId)
        then: "Operation is disallowed because of no space left."
            acceptProposal.isLeft()
            ProposalAcceptingFailed e = acceptProposal.getLeft()
            e.getReason().contains("no space left for animals in shelter")
        where:
            proposals << [10, 11, 200]
    }

    def 'shelter cannot accept accepted proposal'() {
        given: "Prepare any proposal id."
            ProposalId proposalId = anyProposalId()
        and: "Prepare shelter with specified proposal."
            Shelter shelter = shelterWithAcceptedProposal(proposalId)
        when: "Accept accepted proposal."
            def acceptProposal = shelter.accept(proposalId)
        then: "Operation is disallowed."
            acceptProposal.isLeft()
            ProposalAcceptingFailed e = acceptProposal.getLeft()
            e.getReason().contains("proposal is already accepted")
    }

    def 'shelter cannot accept pending proposal'() {
        given: "Prepare any proposal id."
            ProposalId proposalId = anyProposalId()
        and: "Prepare shelter with pending proposal."
            Shelter shelter = shelterWithPendingProposal(proposalId)
        when: "Accept pending proposal."
            def acceptProposal = shelter.accept(proposalId)
        then: "Operation is disallowed."
            acceptProposal.isLeft()
            ProposalAcceptingFailed e = acceptProposal.getLeft()
            e.getReason().contains("proposal is pending")
    }
}