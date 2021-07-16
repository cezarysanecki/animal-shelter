package pl.devcezz.animalshelter.animal;

import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.cqrs.event.Event;
import pl.devcezz.animalshelter.animal.AnimalEvent.AcceptingAnimalSucceeded;
import pl.devcezz.animalshelter.animal.AnimalEvent.AcceptingAnimalWarned;
import pl.devcezz.animalshelter.animal.AnimalEvent.AcceptingAnimalFailed;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.devcezz.animalshelter.animal.AnimalFixture.buildAnimal;

class ShelterTest {

    @DisplayName("Should create succeed event when free space in shelter")
    @Test
    void should_create_succeed_event_when_free_space_in_shelter() {
        ShelterLimits shelterLimits = new ShelterLimits(10, 7);
        Shelter shelter = shelter(shelterLimits, 5);

        Event result = shelter.accept(buildAnimal());

        assertThat(result).isInstanceOf(AcceptingAnimalSucceeded.class);
    }

    @DisplayName("Should create warned event when safe threshold reached")
    @Test
    void should_create_warned_event_when_safe_threshold_reached() {
        ShelterLimits shelterLimits = new ShelterLimits(10, 7);
        Shelter shelter = shelter(shelterLimits, 6);

        Event result = shelter.accept(buildAnimal());

        assertThat(result).isInstanceOf(AcceptingAnimalWarned.class);
    }

    @DisplayName("Should create failed event when capacity is exceeded")
    @Test
    void should_create_failed_event_when_capacity_is_exceeded() {
        ShelterLimits shelterLimits = new ShelterLimits(10, 7);
        Shelter shelter = shelter(shelterLimits, 10);

        Event result = shelter.accept(buildAnimal());

        assertThat(result).isInstanceOf(AcceptingAnimalFailed.class);
    }

    private Shelter shelter(ShelterLimits shelterLimits, Integer amountOfAnimals) {
        return new Shelter(shelterLimits, animals(amountOfAnimals));
    }

    private Set<ShelterAnimal> animals(int amount) {
        return Stream.fill(amount, () -> new ShelterAnimal(new AnimalId(UUID.randomUUID()))).toSet();
    }
}