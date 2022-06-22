package pl.devcezz.shelter.shelter.model;

public interface Shelters {

    Shelter prepareShelter();

    Shelter publish(ShelterEvent event);
}
