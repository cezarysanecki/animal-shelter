package pl.devcezz.shelter.adoption.shelter.application;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.shelter.model.Shelter;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAccepted;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalAcceptedEvents;
import pl.devcezz.shelter.adoption.shelter.model.ShelterEvent.ProposalCanceled;
import pl.devcezz.shelter.adoption.shelter.model.Shelters;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;
import static pl.devcezz.shelter.adoption.shelter.model.ShelterFixture.shelterWithProposals;

class InMemoryShelterRepository implements Shelters {

    private Set<ProposalId> proposals = HashSet.empty();

    @Override
    public Shelter prepareShelter() {
        return shelterWithProposals(proposals);
    }

    @Override
    public Shelter publish(ShelterEvent event) {
        return Match(event).of(
                Case($(instanceOf(ProposalAcceptedEvents.class)), this::handle),
                Case($(instanceOf(ProposalAccepted.class)), this::handle),
                Case($(instanceOf(ProposalCanceled.class)), this::handle),
                Case($(), () -> shelterWithProposals(proposals))
        );
    }

    private Shelter handle(ProposalAcceptedEvents events) {
        ProposalAccepted event = events.getProposalAccepted();
        return handle(event);
    }

    private Shelter handle(ProposalAccepted event) {
        proposals = proposals.add(event.proposalId());
        return shelterWithProposals(proposals);
    }

    private Shelter handle(ProposalCanceled event) {
        proposals = proposals.remove(event.proposalId());
        return shelterWithProposals(proposals);
    }
}
