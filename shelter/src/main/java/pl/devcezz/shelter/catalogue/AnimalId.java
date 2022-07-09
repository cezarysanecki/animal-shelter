package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class AnimalId {

    private String value;

    private AnimalId(UUID value) {
        this.value = value.toString();
    }

    public static AnimalId of(UUID value) {
        return new AnimalId(value);
    }

    public UUID getValue() {
        return UUID.fromString(value);
    }
}
