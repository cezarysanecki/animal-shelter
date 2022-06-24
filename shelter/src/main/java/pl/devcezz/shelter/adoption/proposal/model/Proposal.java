package pl.devcezz.shelter.adoption.proposal.model;

import pl.devcezz.shelter.commons.aggregates.Version;

public interface Proposal {

    ProposalId getProposalId();

    Version getVersion();
}
