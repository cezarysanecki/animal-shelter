package pl.devcezz.animalshelter.shelter.application.command;

import pl.devcezz.cqrs.command.Command;

import java.util.UUID;

public record AdoptAnimalCommand(UUID animalId) implements Command {}