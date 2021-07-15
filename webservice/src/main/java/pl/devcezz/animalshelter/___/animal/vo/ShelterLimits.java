package pl.devcezz.animalshelter.___.animal.vo;

public record ShelterLimits(int capacity, int safeThreshold) {

    public ShelterLimits {
        if (capacity < 0) {
            throw new IllegalArgumentException("Shelter capacity cannot be negative");
        }
        if (safeThreshold < 0) {
            throw new IllegalArgumentException("Shelter safe threshold cannot be negative");
        }
        if (safeThreshold > capacity) {
            throw new IllegalArgumentException("Safe threshold cannot be larger than capacity");
        }
    }
}
