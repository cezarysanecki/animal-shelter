package pl.devcezz.animalshelter.shelter.application;

record ShelterLimits(int capacity, int safeThreshold) {

    ShelterLimits {
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
