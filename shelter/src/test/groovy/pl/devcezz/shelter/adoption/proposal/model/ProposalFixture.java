package pl.devcezz.shelter.adoption.proposal.model;

import java.util.UUID;

public class ProposalFixture {

    public static ProposalId anyProposal() {
        return ProposalId.of(UUID.randomUUID());
    }
}