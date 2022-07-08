package pl.devcezz.shelter.adoption.shelter.infrastructure

import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted.proposalAcceptedNow
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptedEvents
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled.proposalCanceledNow

class CreatingDataModelFromShelterEventsSpec extends Specification {

    ProposalId proposalId = anyProposalId()

    def 'should add proposal on proposalAccepted event'() {
        given: "Create shelter."
            ShelterDatabaseEntity entity = createShelter()
        when: "Accept proposal."
            entity.handle(proposalAccepted())
        then: "There is one accepted proposal."
            entity.acceptedProposals.size() == 1
    }

    def 'should cancel proposal on proposalCanceled event with open ended duration '() {
        given: "Create shelter."
            ShelterDatabaseEntity entity = createShelter()
        when: "Accept proposal."
            entity.handle(proposalAccepted())
        then: "There is one accepted proposal."
            entity.acceptedProposals.size() == 1
        when: "Cancel proposal."
            entity.handle(proposalCanceled())
        then: "There is none accepted proposals."
            entity.acceptedProposals.isEmpty()
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
