package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
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
