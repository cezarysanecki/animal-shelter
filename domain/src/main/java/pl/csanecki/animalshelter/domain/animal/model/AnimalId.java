package pl.csanecki.animalshelter.domain.animal.model;

import lombok.NonNull;
import lombok.Value;

@Value
public class AnimalId {

    @NonNull int animalId;
}
