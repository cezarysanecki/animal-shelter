package pl.devcezz.shelter.shelter.model;

public class ShelterFactory {

    public Shelter create(long acceptedProposalsCount) {
        return new Shelter(acceptedProposalsCount);
    }
}
