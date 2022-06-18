package pl.devcezz.shelter.proposal.exception;

import pl.devcezz.shelter.ShelterException;

import java.util.UUID;

public class AnimalProposalNotFoundException extends ShelterException {

    public AnimalProposalNotFoundException(UUID animalProposalId) {
        super("animal proposal not found: " + animalProposalId);
    }
}
