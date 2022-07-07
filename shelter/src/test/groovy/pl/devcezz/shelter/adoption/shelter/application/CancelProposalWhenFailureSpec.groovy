package pl.devcezz.shelter.adoption.shelter.application

import io.vavr.control.Try
import pl.devcezz.shelter.adoption.proposal.model.ProposalEvent
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.adoption.shelter.model.Shelters
import pl.devcezz.shelter.commons.commands.Result
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAcceptanceFailed.proposalAcceptanceFailedNow
import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId

class CancelProposalWhenFailureSpec extends Specification {

    Shelters shelterRepository = new InMemoryShelterRepository()

    ShelterApplicationConfig config = new ShelterApplicationConfig(shelterRepository)

    AcceptingProposal acceptingProposal = config.acceptingProposal()
    CancelingProposal cancelingProposal = config.cancelingProposal()
    ShelterEventsHandler handler = config.shelterEventsHandler()

    def 'should cancel proposal when acceptance failed'() {
        given:
            ProposalId proposalId = anyProposalId()
        and:
            acceptingProposal.acceptProposal(anAcceptCommand(proposalId))
        when:
            handler.handle(proposalAcceptanceFailedNow("reason", proposalId))
        then:
            Try<Result> cancellationResult = cancelingProposal.cancelProposal(aCancelCommand(proposalId))
        then:
            cancellationResult.isSuccess()
            cancellationResult.get() == Result.Rejection
    }

    CancelProposalCommand aCancelCommand(ProposalId proposalId) {
        return new CancelProposalCommand(proposalId)
    }

    AcceptProposalCommand anAcceptCommand(ProposalId proposalId) {
        return new AcceptProposalCommand(proposalId)
    }
}
