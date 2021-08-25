package pl.devcezz.animalshelter.animal;

public interface Animals {

    void save(Animal animal);

    void publish(AnimalEvent event);
}
