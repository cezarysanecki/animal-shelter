package pl.devcezz.animalshelter.notification;

import java.util.Objects;
import java.util.UUID;

record ZookeeperId(UUID value) {

    ZookeeperId {
        if (value == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ZookeeperId zookeeperId = (ZookeeperId) o;
        return Objects.equals(value, zookeeperId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
