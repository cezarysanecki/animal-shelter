package pl.devcezz.shelter.proposal.application;

import io.vavr.control.Option;
import pl.devcezz.shelter.proposal.model.PendingProposal;
import pl.devcezz.shelter.proposal.model.ProposalId;

@FunctionalInterface
public interface FindPendingProposal {

    Option<PendingProposal> findPendingProposalBy(ProposalId proposalId);
}
