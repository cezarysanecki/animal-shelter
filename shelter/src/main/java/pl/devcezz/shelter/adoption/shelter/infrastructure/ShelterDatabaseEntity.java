package pl.devcezz.shelter.adoption.shelter.infrastructure;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptedEvents;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ShelterDatabaseEntity {

    final Set<AcceptedProposalDatabaseEntity> acceptedProposals;

    ShelterDatabaseEntity() {
        this.acceptedProposals = new HashSet<>();
    }

    Set<UUID> extractAcceptedProposalsIds() {
        return acceptedProposals.stream()
                .map(AcceptedProposalDatabaseEntity::getProposalId)
                .collect(Collectors.toUnmodifiableSet());
    }

    ShelterDatabaseEntity handle(ShelterEvent event) {
        return Match(event).of(
                Case($(instanceOf(ProposalAcceptedEvents.class)), this::handle),
                Case($(instanceOf(ProposalAccepted.class)), this::handle),
                Case($(instanceOf(ProposalCanceled.class)), this::handle),
                Case($(), () -> this)
        );
    }

    private ShelterDatabaseEntity handle(ProposalAcceptedEvents events) {
        ProposalAccepted event = events.getProposalAccepted();
        return handle(event);
    }

    private ShelterDatabaseEntity handle(ProposalAccepted event) {
        return acceptProposal(event.getProposalId());
    }

    private ShelterDatabaseEntity handle(ProposalCanceled event) {
        return cancelProposal(event.getProposalId());
    }

    private ShelterDatabaseEntity acceptProposal(UUID proposalId) {
        acceptedProposals.add(new AcceptedProposalDatabaseEntity(proposalId));
        return this;
    }

    private ShelterDatabaseEntity cancelProposal(UUID proposalId) {
        acceptedProposals.removeIf(acceptedProposal -> acceptedProposal.getProposalId().equals(proposalId));
        return this;
    }
}
