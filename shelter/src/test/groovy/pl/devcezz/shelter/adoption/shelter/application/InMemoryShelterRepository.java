package pl.devcezz.shelter.adoption.shelter.application;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.shelter.model.Shelter;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptedEvents;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalConfirmed;
import pl.devcezz.shelter.adoption.shelter.model.ShelterFixture;
import pl.devcezz.shelter.adoption.shelter.model.Shelters;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

class InMemoryShelterRepository implements Shelters {

    private Set<ProposalId> acceptedProposals = HashSet.empty();
    private Set<ProposalId> pendingProposals = HashSet.empty();

    @Override
    public Shelter prepareShelter() {
        return ShelterFixture.shelterWithProposals(acceptedProposals, pendingProposals);
    }

    @Override
    public Shelter publish(ShelterEvent event) {
        return Match(event).of(
                Case($(instanceOf(ProposalAcceptedEvents.class)), this::handle),
                Case($(instanceOf(ProposalAccepted.class)), this::handle),
                Case($(instanceOf(ProposalCanceled.class)), this::handle),
                Case($(instanceOf(ProposalConfirmed.class)), this::handle),
                Case($(), () -> ShelterFixture.shelterWithAcceptedProposals(acceptedProposals))
        );
    }

    private Shelter handle(ProposalAcceptedEvents events) {
        ProposalAccepted event = events.getProposalAccepted();
        return handle(event);
    }

    private Shelter handle(ProposalAccepted event) {
        pendingProposals = pendingProposals.add(event.proposalId());
        return prepareShelter();
    }

    private Shelter handle(ProposalCanceled event) {
        pendingProposals = pendingProposals.remove(event.proposalId());
        acceptedProposals = acceptedProposals.remove(event.proposalId());
        return prepareShelter();
    }

    private Shelter handle(ProposalConfirmed event) {
        pendingProposals = pendingProposals.remove(event.proposalId());
        acceptedProposals = acceptedProposals.add(event.proposalId());
        return prepareShelter();
    }
}
