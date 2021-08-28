package pl.devcezz.animalshelter.animal;

import pl.devcezz.cqrs.command.Command;

import java.util.UUID;

public record AdoptAnimalCommand(UUID animalId) implements Command {}