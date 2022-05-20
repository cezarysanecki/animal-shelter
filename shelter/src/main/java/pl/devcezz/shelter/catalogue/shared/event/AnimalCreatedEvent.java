package pl.devcezz.shelter.catalogue.shared.event;

import lombok.Value;

import java.util.UUID;

@Value
public class AnimalCreatedEvent {

    UUID animalId;
}
