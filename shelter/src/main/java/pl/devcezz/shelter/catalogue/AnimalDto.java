package pl.devcezz.shelter.catalogue;

import java.util.UUID;

public record AnimalDto(UUID animalId,
                        String name,
                        Integer age,
                        String species,
                        String gender) {
}
