package pl.devcezz.animalshelter.shelter.command;

import pl.devcezz.cqrs.command.Command;

import java.util.UUID;

public record AcceptAnimalCommand(
        UUID animalId, String animalName, String animalSpecies,
        int animalAge, String animalGender) implements Command {}