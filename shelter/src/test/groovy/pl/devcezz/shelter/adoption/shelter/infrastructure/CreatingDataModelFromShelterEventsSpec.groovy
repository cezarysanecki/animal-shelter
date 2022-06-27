package pl.devcezz.shelter.adoption.shelter.infrastructure

import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposal
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted.proposalAcceptedNow
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptedEvents
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled.proposalCanceledNow

class CreatingDataModelFromShelterEventsSpec extends Specification {

    ProposalId proposalId = anyProposal()

    def 'should add proposal on proposalAccepted event'() {
        given:
            ShelterDatabaseEntity entity = createShelter()
        when:
            entity.handle(proposalAccepted())
        then:
            entity.acceptedProposals.size() == 1
    }

    def 'should cancel proposal on proposalCanceled event with open ended duration '() {
        given:
            ShelterDatabaseEntity entity = createShelter()
        when:
            entity.handle(proposalAccepted())
        then:
            entity.acceptedProposals.size() == 1
        when:
            entity.handle(proposalCanceled())
        then:
            entity.acceptedProposals.size() == 0
    }

    ShelterDatabaseEntity createShelter() {
        return new ShelterDatabaseEntity()
    }

    ShelterEvent proposalAccepted() {
        return ProposalAcceptedEvents.events(
                proposalAcceptedNow(proposalId))
    }

    ShelterEvent proposalCanceled() {
        return proposalCanceledNow(proposalId)
    }

}
