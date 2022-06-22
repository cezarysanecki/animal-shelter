package pl.devcezz.shelter.proposal.model;

import pl.devcezz.shelter.proposal.model.Shelter;
import pl.devcezz.shelter.proposal.model.ShelterEvent;

public interface Shelters {

    Shelter prepareShelter();

    void publish(ShelterEvent event);
}
