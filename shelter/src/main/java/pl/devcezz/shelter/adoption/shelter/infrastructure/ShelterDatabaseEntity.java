package pl.devcezz.shelter.adoption.shelter.infrastructure;

import io.vavr.API;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ShelterDatabaseEntity {

    final Set<AcceptedProposalDatabaseEntity> acceptedProposals;

    ShelterDatabaseEntity() {
        this.acceptedProposals = new HashSet<>();
    }

    int countAcceptedProposals() {
        return acceptedProposals.size();
    }

    ShelterDatabaseEntity handle(ShelterEvent event) {
        return API.Match(event).of(
                API.Case(API.$(instanceOf(ShelterEvent.ProposalAcceptedEvents.class)), this::handle),
                API.Case(API.$(instanceOf(ShelterEvent.ProposalAccepted.class)), this::handle)
        );
    }

    private ShelterDatabaseEntity handle(ShelterEvent.ProposalAcceptedEvents events) {
        ShelterEvent.ProposalAccepted event = events.getProposalAccepted();
        return handle(event);
    }

    private ShelterDatabaseEntity handle(ShelterEvent.ProposalAccepted event) {
        return acceptProposal(event.getProposalId());
    }

    private ShelterDatabaseEntity acceptProposal(UUID proposalId) {
        acceptedProposals.add(new AcceptedProposalDatabaseEntity(proposalId));
        return this;
    }
}
