package pl.devcezz.shelter.adoption.shelter.model;

public interface Shelters {

    Shelter prepareShelter();

    Shelter publish(ShelterEvent event);
}
