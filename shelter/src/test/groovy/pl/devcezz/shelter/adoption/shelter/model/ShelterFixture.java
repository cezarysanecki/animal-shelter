package pl.devcezz.shelter.adoption.shelter.model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import pl.devcezz.shelter.adoption.proposal.model.AcceptedProposal;
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.commons.aggregates.Version;

import java.util.UUID;

import static io.vavr.collection.HashSet.rangeClosed;

public class ShelterFixture {

    public static Shelter shelter() {
        return new Shelter(HashSet.of());
    }

    public static Shelter shelterWithProposals(int numberOfProposals) {
        return new Shelter(acceptedProposals(numberOfProposals));
    }

    public static Shelter shelterWithProposal(AcceptedProposal acceptedProposal) {
        return new Shelter(HashSet.of(acceptedProposal.getProposalId()));
    }

    public static Set<ProposalId> acceptedProposals(int numberOfProposals) {
        return rangeClosed(1, numberOfProposals)
                .map(i -> ProposalId.of(UUID.randomUUID()))
                .toSet();
    }

    public static PendingProposal aPendingProposal() {
        return new PendingProposal(ProposalId.of(UUID.randomUUID()), version0());
    }

    public static AcceptedProposal anAcceptedProposal() {
        return new AcceptedProposal(ProposalId.of(UUID.randomUUID()), version0());
    }

    public static Version version0() {
        return Version.zero();
    }
}
