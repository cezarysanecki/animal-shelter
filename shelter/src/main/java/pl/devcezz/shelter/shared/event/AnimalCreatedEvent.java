package pl.devcezz.shelter.shared.event;

import lombok.Value;

import java.util.UUID;

@Value
public class AnimalCreatedEvent {

    UUID animalId;
}
