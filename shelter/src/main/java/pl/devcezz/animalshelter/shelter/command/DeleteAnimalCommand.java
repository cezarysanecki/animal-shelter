package pl.devcezz.animalshelter.shelter.command;

import pl.devcezz.cqrs.command.Command;

import java.util.UUID;

public record DeleteAnimalCommand(UUID animalId) implements Command {}