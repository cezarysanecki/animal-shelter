package pl.devcezz.animalshelter.animal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.animal.AnimalEvent.AcceptingAnimalFailed;
import pl.devcezz.animalshelter.animal.AnimalEvent.AcceptingAnimalSucceeded;
import pl.devcezz.animalshelter.animal.AnimalEvent.AcceptingAnimalWarned;
import pl.devcezz.animalshelter.commons.exception.AcceptingAnimalRejectedException;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.devcezz.animalshelter.animal.ShelterFixture.shelter;
import static pl.devcezz.animalshelter.animal.ShelterFixture.shelterLimits;

class AcceptingAnimalTest {

    private Animals animals;
    private ShelterFactory shelterFactory;

    private AcceptingAnimal acceptingAnimal;

    @BeforeEach
    void setUp() {
        animals = mock(Animals.class);
        shelterFactory = mock(ShelterFactory.class);

        acceptingAnimal = new AcceptingAnimal(animals, shelterFactory);
    }

    @DisplayName("Should successfully accept animal when shelter has enough space")
    @Test
    void should_successfully_accept_animal_when_shelter_has_enough_space() {
        when(shelterFactory.create()).thenReturn(shelter(shelterLimits(10, 7), 5));

        acceptingAnimal.handle(command());

        verify(animals).save(any(Animal.class));
        verify(animals).publish(any(AcceptingAnimalSucceeded.class));
    }

    @DisplayName("Should accept animal with warning when shelter space reached safe threshold")
    @Test
    void should_accept_animal_with_warning_when_shelter_space_reached_safe_threshold() {
        when(shelterFactory.create()).thenReturn(shelter(shelterLimits(10, 7), 6));

        acceptingAnimal.handle(command());

        verify(animals).save(any(Animal.class));
        verify(animals).publish(any(AcceptingAnimalWarned.class));
    }

    @DisplayName("Should fail when accepting animal because of running out of space")
    @Test
    void should_fail_when_accepting_animal_because_of_running_out_of_space() {
        when(shelterFactory.create()).thenReturn(shelter(shelterLimits(10, 7), 10));

        assertThatExceptionOfType(AcceptingAnimalRejectedException.class)
                .isThrownBy(() -> acceptingAnimal.handle(command()));

        verify(animals, never()).save(any(Animal.class));
        verify(animals).publish(any(AcceptingAnimalFailed.class));
    }

    private AcceptAnimalCommand command() {
        return new AcceptAnimalCommand(
                UUID.randomUUID(),
                "Azor",
                "Dog",
                2
        );
    }
}