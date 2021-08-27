package pl.devcezz.animalshelter.animal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.animal.AnimalEvent.AcceptingAnimalFailed;
import pl.devcezz.animalshelter.animal.AnimalEvent.AcceptingAnimalSucceeded;
import pl.devcezz.animalshelter.animal.AnimalEvent.AcceptingAnimalWarned;
import pl.devcezz.cqrs.event.Event;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.devcezz.animalshelter.animal.AnimalFixture.buildAnimal;
import static pl.devcezz.animalshelter.animal.ShelterFixture.shelter;
import static pl.devcezz.animalshelter.animal.ShelterFixture.shelterLimits;

class ShelterTest {

    @DisplayName("Should create succeed event when free space in shelter")
    @Test
    void should_create_succeed_event_when_free_space_in_shelter() {
        Shelter shelter = shelter(shelterLimits(10, 7), 5);

        Event result = shelter.accept(buildAnimal());

        assertThat(result).isInstanceOf(AcceptingAnimalSucceeded.class);
    }

    @DisplayName("Should create warned event when safe threshold reached")
    @Test
    void should_create_warned_event_when_safe_threshold_reached() {
        Shelter shelter = shelter(shelterLimits(10, 7), 6);

        Event result = shelter.accept(buildAnimal());

        assertThat(result).isInstanceOf(AcceptingAnimalWarned.class);
    }

    @DisplayName("Should create failed event when capacity is exceeded")
    @Test
    void should_create_failed_event_when_capacity_is_exceeded() {
        Shelter shelter = shelter(shelterLimits(10, 7), 10);

        AcceptingAnimalFailed result = (AcceptingAnimalFailed) shelter.accept(buildAnimal());

        assertThat(result.getReason()).isEqualTo("not enough space in shelter");
    }
}