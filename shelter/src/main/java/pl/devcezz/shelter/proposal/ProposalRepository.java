package pl.devcezz.shelter.proposal;

import io.vavr.control.Option;

public interface ProposalRepository {

    Option<Proposal> findBy(ProposalId proposalId);

    void save(Proposal proposal);
}
