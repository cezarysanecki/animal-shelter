package pl.devcezz.shelter.adoption.proposal.model;

import pl.devcezz.shelter.commons.aggregates.Version;

import java.util.UUID;

public class ProposalFixture {

    public static ProposalId anyProposalId() {
        return ProposalId.of(UUID.randomUUID());
    }

    public static PendingProposal pendingProposal() {
        return pendingProposal(anyProposalId());
    }

    public static PendingProposal pendingProposal(ProposalId proposalId) {
        return new PendingProposal(proposalId, version0());
    }

    public static AcceptedProposal acceptedProposal() {
        return acceptedProposal(anyProposalId());
    }

    public static AcceptedProposal acceptedProposal(ProposalId proposalId) {
        return new AcceptedProposal(proposalId, version0());
    }

    public static Version version0() {
        return Version.zero();
    }
}