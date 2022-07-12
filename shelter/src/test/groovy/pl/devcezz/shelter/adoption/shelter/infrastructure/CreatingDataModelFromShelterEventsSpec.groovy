package pl.devcezz.shelter.adoption.shelter.infrastructure

import pl.devcezz.shelter.adoption.proposal.model.ProposalId
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent
import spock.lang.Specification

import static pl.devcezz.shelter.adoption.proposal.model.ProposalEvent.ProposalAcceptanceConfirmed.proposalAcceptanceConfirmedNow
import static pl.devcezz.shelter.adoption.proposal.model.ProposalFixture.anyProposalId
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted.proposalAcceptedNow
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptedEvents
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled.proposalCanceledNow
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalConfirmed.proposalConfirmedNow

class CreatingDataModelFromShelterEventsSpec extends Specification {

    ProposalId proposalId = anyProposalId()

    def 'should add proposal on proposalAccepted event'() {
        given: "Create shelter."
            ShelterDatabaseEntity entity = createShelter()
        when: "Accept proposal."
            entity.handle(proposalAccepted())
        then: "There is one pending proposal."
            numberOfPendingProposalsIsEqualedTo(entity, 1)
    }

    def 'should cancel proposal on proposalCanceled event'() {
        given: "Create shelter."
            ShelterDatabaseEntity entity = createShelter()
        when: "Accept proposal."
            entity.handle(proposalAccepted())
        then: "There is one pending proposal."
            numberOfPendingProposalsIsEqualedTo(entity, 1)
        when: "Cancel proposal."
            entity.handle(proposalCanceled())
        then: "There is none pending proposals."
            numberOfPendingProposalsIsEqualedTo(entity, 0)
    }

    def 'should confirm proposal on proposalConfirmed event'() {
        given: "Create shelter."
            ShelterDatabaseEntity entity = createShelter()
        when: "Accept proposal."
            entity.handle(proposalAccepted())
        then: "There is one pending proposal."
            numberOfPendingProposalsIsEqualedTo(entity, 1)
        and: "There is none confirmed proposals."
            numberOfConfirmedProposalsIsEqualedTo(entity, 0)
        when: "Confirm proposal."
            entity.handle(proposalConfirmed())
        then: "There is none pending proposals."
            numberOfPendingProposalsIsEqualedTo(entity, 0)
        then: "There is one confirmed proposal."
            numberOfConfirmedProposalsIsEqualedTo(entity, 1)
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

    ShelterEvent proposalConfirmed() {
        return proposalConfirmedNow(proposalId)
    }

    boolean numberOfPendingProposalsIsEqualedTo(ShelterDatabaseEntity entity, int amount) {
        return entity.acceptedProposals
                .stream()
                .filter(AcceptedProposalDatabaseEntity::isPending)
                .count() == amount
    }

    boolean numberOfConfirmedProposalsIsEqualedTo(ShelterDatabaseEntity entity, int amount) {
        return entity.acceptedProposals
                .stream()
                .filter(AcceptedProposalDatabaseEntity::isConfirmed)
                .count() == amount
    }

}
