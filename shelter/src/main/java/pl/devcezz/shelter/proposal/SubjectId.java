package pl.devcezz.shelter.proposal;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
class SubjectId {

    private String value;

    private SubjectId(UUID value) {
        this.value = value.toString();
    }

    static SubjectId of(UUID value) {
        return new SubjectId(value);
    }

    UUID getValue() {
        return UUID.fromString(value);
    }
}
