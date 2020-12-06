package pl.csanecki.animalshelter.domain.service.entity;

import lombok.NonNull;
import lombok.Value;

@Value
public class AnimalId {

    @NonNull
    Long animalId;
}
