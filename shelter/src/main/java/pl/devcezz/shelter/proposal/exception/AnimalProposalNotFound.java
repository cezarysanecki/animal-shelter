package pl.devcezz.shelter.proposal.exception;

import pl.devcezz.shelter.ShelterException;

import java.util.UUID;

public class AnimalProposalNotFound extends ShelterException {

    public AnimalProposalNotFound(UUID animalProposalId) {
        super("animal proposal not found: " + animalProposalId);
    }
}
