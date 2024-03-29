package pl.devcezz.shelter.adoption.proposal.model;

import pl.devcezz.shelter.commons.aggregates.Version;

import java.util.UUID;

public class ProposalFixture {

    public static ProposalId anyProposalId() {
        return ProposalId.of(UUID.randomUUID());
    }

    public static PendingProposal pendingProposal(ProposalId proposalId) {
        return new PendingProposal(proposalId, version0());
    }

    public static PendingProposal pendingProposal() {
        return pendingProposal(anyProposalId());
    }

    public static AcceptedProposal acceptedProposal() {
        return new AcceptedProposal(anyProposalId(), version0());
    }

    public static DeletedProposal deletedProposal() {
        return new DeletedProposal(anyProposalId(), version0());
    }

    public static Version version0() {
        return Version.zero();
    }
}