package pl.devcezz.shelter.adoption.shelter.model;

import io.vavr.collection.Set;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;

import java.util.UUID;

public class ShelterFactory {

    public Shelter create(Set<UUID> acceptedProposalsIds, Set<UUID> pendingProposalsIds) {
        return new Shelter(
                acceptedProposalsIds.map(ProposalId::of),
                pendingProposalsIds.map(ProposalId::of));
    }
}
