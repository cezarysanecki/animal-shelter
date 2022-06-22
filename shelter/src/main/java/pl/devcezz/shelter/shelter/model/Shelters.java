package pl.devcezz.shelter.shelter.model;

public interface Shelters {

    Shelter prepareShelter();

    void publish(ShelterEvent event);
}
