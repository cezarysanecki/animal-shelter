package pl.devcezz.shelter.adoption.shelter.application;

import io.vavr.control.Option;
import pl.devcezz.shelter.adoption.proposal.model.AcceptedProposal;
import pl.devcezz.shelter.adoption.proposal.model.PendingProposal;
import pl.devcezz.shelter.adoption.proposal.model.ProposalId;

@FunctionalInterface
public interface FindAcceptedProposal {

    Option<AcceptedProposal> findAcceptedProposalBy(ProposalId proposalId);
}
