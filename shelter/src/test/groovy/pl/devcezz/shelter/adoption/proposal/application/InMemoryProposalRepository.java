package pl.devcezz.shelter.adoption.proposal.application;

import io.vavr.control.Option;
import pl.devcezz.shelter.adoption.proposal.model.Proposal;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;
import pl.devcezz.shelter.adoption.proposal.model.Proposals;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class InMemoryProposalRepository implements Proposals {

    private final Map<ProposalId, Proposal> database = new ConcurrentHashMap<>();

    @Override
    public Option<Proposal> findBy(ProposalId proposalId) {
        return Option.of(database.get(proposalId));
    }

    @Override
    public void save(Proposal proposal) {
        database.put(proposal.getProposalId(), proposal);
    }
}
