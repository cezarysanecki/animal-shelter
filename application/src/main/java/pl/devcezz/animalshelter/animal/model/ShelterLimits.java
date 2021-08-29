package pl.devcezz.animalshelter.animal.model;

public record ShelterLimits(int capacity, int safeThreshold) {

    public ShelterLimits {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity cannot be negative");
        }
        if (safeThreshold < 0) {
            throw new IllegalArgumentException("safe threshold cannot be negative");
        }
        if (safeThreshold > capacity) {
            throw new IllegalArgumentException("safe threshold cannot be greater than capacity");
        }
    }
}
