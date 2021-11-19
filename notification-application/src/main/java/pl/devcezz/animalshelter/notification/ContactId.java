package pl.devcezz.animalshelter.notification;

import java.util.Objects;
import java.util.UUID;

record ContactId(UUID value) {

    ContactId {
        if (value == null) {
            throw new IllegalArgumentException("contactId cannot be null");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ContactId contactId = (ContactId) o;
        return Objects.equals(value, contactId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
