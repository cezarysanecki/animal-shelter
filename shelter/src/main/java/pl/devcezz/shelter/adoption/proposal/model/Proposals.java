package pl.devcezz.shelter.adoption.proposal.model;

import io.vavr.control.Option;

public interface Proposals {

    Option<Proposal> findBy(ProposalId proposalId);

    void save(Proposal proposal);
}
