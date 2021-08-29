package pl.devcezz.animalshelter.read.result;

import java.util.UUID;

public record AnimalDto(
        UUID animalId,
        String name,
        String kind,
        Integer age,
        Boolean inShelter
) {}
