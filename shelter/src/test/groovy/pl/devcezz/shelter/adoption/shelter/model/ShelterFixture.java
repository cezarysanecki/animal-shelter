package pl.devcezz.shelter.adoption.shelter.model;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;

import java.util.UUID;

import static io.vavr.collection.HashSet.rangeClosed;

public class ShelterFixture {

    public static Shelter shelter() {
        return new Shelter(HashSet.of(), HashSet.of());
    }

    public static Shelter shelterWithProposals(Set<ProposalId> acceptedProposals, Set<ProposalId> pendingProposals) {
        return new Shelter(acceptedProposals, pendingProposals);
    }

    public static Shelter shelterWithAcceptedProposal(ProposalId proposalId) {
        return new Shelter(HashSet.of(proposalId), HashSet.of());
    }

    public static Shelter shelterWithAcceptedProposals(Set<ProposalId> proposals) {
        return new Shelter(proposals, HashSet.of());
    }

    public static Shelter shelterWithAcceptedProposals(int amount) {
        return new Shelter(proposals(amount), HashSet.of());
    }

    public static Set<ProposalId> proposals(int numberOfProposals) {
        return rangeClosed(1, numberOfProposals)
                .map(i -> ProposalId.of(UUID.randomUUID()))
                .toSet();
    }
}
