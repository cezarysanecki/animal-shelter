package pl.devcezz.shelter.proposal.exception;

import pl.devcezz.shelter.ShelterException;

import java.util.UUID;

public class ProposalNotFoundException extends ShelterException {

    public ProposalNotFoundException(UUID subjectId) {
        super("proposal not found: " + subjectId);
    }
}
