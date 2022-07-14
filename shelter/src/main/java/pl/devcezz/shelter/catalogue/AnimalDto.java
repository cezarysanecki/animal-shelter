package pl.devcezz.shelter.catalogue;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class AnimalDto {
    UUID animalId;
    String name;
    Integer age;
    String species;
    String gender;
}
