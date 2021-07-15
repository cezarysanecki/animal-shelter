package pl.devcezz.animalshelter.animal.vo;

import java.util.UUID;

public record AnimalId(UUID value) {

    public AnimalId {
        if (value == null) {
            throw new IllegalArgumentException("Animal id cannot be null");
        }
    }
}
