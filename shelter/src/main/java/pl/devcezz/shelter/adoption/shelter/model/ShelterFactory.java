package pl.devcezz.shelter.adoption.shelter.model;

public class ShelterFactory {

    public Shelter create(long acceptedProposalsCount) {
        return new Shelter(acceptedProposalsCount);
    }
}
