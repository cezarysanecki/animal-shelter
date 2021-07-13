package pl.csanecki.animalshelter.___;

import io.vavr.collection.List;

public class AnimalsInShelter {

    private final List<Animal> animals;

    public AnimalsInShelter(final List<Animal> animals) {
        this.animals = animals;
    }

    public int count() {
        return animals.size();
    }
}
