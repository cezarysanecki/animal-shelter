package pl.devcezz.shelter.adoption.shelter.application

import io.vavr.control.Try
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.adoption.shelter.model.Shelters
import pl.devcezz.shelter.commons.commands.Result
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAcceptanceFailed.proposalAcceptanceFailedNow
import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId

class ConfirmProposalWhenSuccessSpec extends Specification {

    Shelters shelterRepository = new InMemoryShelterRepository()

    ShelterApplicationConfig config = new ShelterApplicationConfig(shelterRepository)

    AcceptingProposal acceptingProposal = config.acceptingProposal()
    ShelterEventsHandler handler = config.shelterEventsHandler()

    def 'should confirm proposal acceptance when it succeeded'() {
        given: "Prepare any proposal id."
            ProposalId proposalId = anyProposalId()
        and: "Accepted proposal."
            acceptingProposal.acceptProposal(anAcceptCommand(proposalId))
        when: "Handle response that acceptance is confirmed."
            handler.handle(proposalAcceptanceConfirmed(proposalId))
        then: "Try to accept proposal once again."
            Try<Result> acceptanceResult = acceptingProposal.acceptProposal(anAcceptCommand(proposalId))
        then: "Operation is rejected."
            acceptanceResult.isSuccess()
            acceptanceResult.get() == Result.Rejection
    }

    CancelProposalCommand aCancelCommand(ProposalId proposalId) {
        return new CancelProposalCommand(proposalId)
    }

    AcceptProposalCommand anAcceptCommand(ProposalId proposalId) {
        return new AcceptProposalCommand(proposalId)
    }
}
