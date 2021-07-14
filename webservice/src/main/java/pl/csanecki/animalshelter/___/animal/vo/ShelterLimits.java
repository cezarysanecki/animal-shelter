package pl.csanecki.animalshelter.___.animal.vo;

public class ShelterLimits {

    private final int capacity;
    private final int safeThreshold;

    public ShelterLimits(final int capacity, final int safeThreshold) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Shelter capacity cannot be negative");
        }
        if (safeThreshold < 0) {
            throw new IllegalArgumentException("Shelter safe threshold cannot be negative");
        }
        if (safeThreshold > capacity) {
            throw new IllegalArgumentException("Safe threshold cannot be larger than capacity");
        }
        this.capacity = capacity;
        this.safeThreshold = safeThreshold;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSafeThreshold() {
        return safeThreshold;
    }
}
