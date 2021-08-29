package pl.devcezz.animalshelter.read.result;

import java.time.Instant;
import java.util.UUID;

public record AnimalInfoDto(
        UUID animalId,
        String name,
        String kind,
        Integer age,
        Instant admittedAt,
        Instant adoptedAt
) {}
