package pl.devcezz.shelter.adoption.shelter.application

import io.vavr.control.Try
import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.commons.commands.Result
import spock.lang.Specification

import java.util.stream.IntStream

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId

class ProposalProcessSpec extends Specification {

    ShelterApplicationConfig config = new ShelterApplicationConfig(new InMemoryShelterRepository())

    CancelingProposal cancelingProposal = config.cancelingProposal()
    AcceptingProposal acceptingProposal = config.acceptingProposal()

    def 'should process accepting and cancelling proposals'() {
        when: "Cancel proposal when there is none accepted."
            Try<Result> cancellationResult = cancelingProposal.cancelProposal(aCancelCommand(anyProposalId()))
        then: "Operation is rejected."
            cancellationResult.isSuccess()
            cancellationResult.get() == Result.Rejection
        when: "Prepare any proposal id."
            ProposalId acceptedProposalId = anyProposalId()
        and: "Accepted proposal."
            Try<Result> acceptationResult = acceptingProposal.acceptProposal(anAcceptCommand(acceptedProposalId))
        then: "Operation is successful."
            acceptationResult.isSuccess()
            acceptationResult.get() == Result.Success
        when: "Accept 9 proposals."
            acceptProposals(9)
        and: "Accept next proposal."
            Try<Result> secondAcceptationResult = acceptingProposal.acceptProposal(anAcceptCommand(anyProposalId()))
        then: "Operation is rejected because lack of space."
            secondAcceptationResult.isSuccess()
            secondAcceptationResult.get() == Result.Rejection
        when: "Cancel proposal."
            Try<Result> secondCancellationResult = cancelingProposal.cancelProposal(aCancelCommand(acceptedProposalId))
        then: "Operation is successful."
            secondCancellationResult.isSuccess()
            secondCancellationResult.get() == Result.Success
    }

    CancelProposalCommand aCancelCommand(ProposalId proposalId) {
        return new CancelProposalCommand(proposalId)
    }

    AcceptProposalCommand anAcceptCommand(ProposalId proposalId) {
        return new AcceptProposalCommand(proposalId)
    }

    void acceptProposals(int numberOfProposals) {
        IntStream.range(0, numberOfProposals)
                .forEach(index -> acceptingProposal.acceptProposal(anAcceptCommand(anyProposalId())))
    }
}
