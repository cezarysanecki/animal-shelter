package pl.devcezz.shelter.shelter.infrastructure;

import io.vavr.API;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.shelter.model.ShelterEvent;
import pl.devcezz.shelter.shelter.model.ShelterEvent.ProposalAccepted;
import pl.devcezz.shelter.shelter.model.ShelterEvent.ProposalAcceptedEvents;

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
                Case($(instanceOf(ProposalAcceptedEvents.class)), this::handle),
                Case($(instanceOf(ProposalAccepted.class)), this::handle)
        );
    }

    private ShelterDatabaseEntity handle(ProposalAcceptedEvents events) {
        ProposalAccepted event = events.getProposalAccepted();
        return handle(event);
    }

    private ShelterDatabaseEntity handle(ProposalAccepted event) {
        return acceptProposal(event.getProposalId());
    }

    private ShelterDatabaseEntity acceptProposal(UUID proposalId) {
        acceptedProposals.add(new AcceptedProposalDatabaseEntity(proposalId));
        return this;
    }
}
