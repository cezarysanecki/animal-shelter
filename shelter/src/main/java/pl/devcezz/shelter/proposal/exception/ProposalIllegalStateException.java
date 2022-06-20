package pl.devcezz.shelter.proposal.exception;

import pl.devcezz.shelter.ShelterException;

import java.util.UUID;

public class ProposalIllegalStateException extends ShelterException {

    private ProposalIllegalStateException(String action, UUID subjectId) {
        super("cannot " + action + " proposal: " + subjectId);
    }

    public static ProposalIllegalStateException exceptionCannotAccept(UUID subjectId) {
        return new ProposalIllegalStateException("accept", subjectId);
    }

    public static ProposalIllegalStateException exceptionCannotDecline(UUID subjectId) {
        return new ProposalIllegalStateException("decline", subjectId);
    }
}
