package pl.devcezz.shelter.proposal.exception;

import pl.devcezz.shelter.ShelterException;
import pl.devcezz.shelter.proposal.ProposalId;

import java.util.UUID;

public class ProposalIllegalStateException extends ShelterException {

    private ProposalIllegalStateException(String action, ProposalId proposalId) {
        super("cannot " + action + " proposal: " + proposalId.getValue());
    }

    public static ProposalIllegalStateException exceptionCannotAccept(ProposalId proposalId) {
        return new ProposalIllegalStateException("accept", proposalId);
    }

    public static ProposalIllegalStateException exceptionCannotDecline(ProposalId proposalId) {
        return new ProposalIllegalStateException("decline", proposalId);
    }

    public static ProposalIllegalStateException exceptionCannotDelete(ProposalId proposalId) {
        return new ProposalIllegalStateException("delete", proposalId);
    }
}
