package pl.devcezz.shelter.catalogue;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
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
