package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
class AnimalId {

    private String value;

    private AnimalId(UUID value) {
        this.value = value.toString();
    }

    static AnimalId of(UUID value) {
        return new AnimalId(value);
    }

    UUID getValue() {
        return UUID.fromString(value);
    }
}
