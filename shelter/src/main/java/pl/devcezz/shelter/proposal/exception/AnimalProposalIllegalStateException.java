package pl.devcezz.shelter.proposal.exception;

import pl.devcezz.shelter.ShelterException;

import java.util.UUID;

public class AnimalProposalIllegalStateException extends ShelterException {

    private AnimalProposalIllegalStateException(String action) {
        super("cannot " + action + " proposal");
    }

    public static AnimalProposalIllegalStateException exceptionCannotAccept() {
        return new AnimalProposalIllegalStateException("accept");
    }

    public static AnimalProposalIllegalStateException exceptionCannotDecline() {
        return new AnimalProposalIllegalStateException("decline");
    }
}
