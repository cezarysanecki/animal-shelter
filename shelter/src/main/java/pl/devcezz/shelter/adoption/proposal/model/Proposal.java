package pl.devcezz.shelter.adoption.proposal.model;

import pl.devcezz.shelter.shared.Version;

public interface Proposal {

    ProposalId getProposalId();

    Version getVersion();
}
