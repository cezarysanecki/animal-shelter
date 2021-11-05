package pl.devcezz.animalshelter.shelter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.shelter.command.AcceptAnimalCommand;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AcceptingAnimalFailed;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AcceptingAnimalSucceeded;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AcceptingAnimalWarned;
import pl.devcezz.animalshelter.shelter.exception.AcceptingAnimalRejectedException;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.devcezz.animalshelter.shelter.ShelterFixture.availableAnimals;
import static pl.devcezz.animalshelter.shelter.ShelterFixture.shelterLimits;

class AcceptingAnimalTest {

    private Animals animals;
    private ShelterRepository shelterRepository;

    private AcceptingAnimal acceptingAnimal;

    @BeforeEach
    void setUp() {
        animals = mock(Animals.class);
        shelterRepository = mock(ShelterRepository.class);

        acceptingAnimal = new AcceptingAnimal(animals, new ShelterFactory(shelterRepository));
    }

    @DisplayName("Should successfully accept animal when shelter has enough space")
    @Test
    void should_successfully_accept_animal_when_shelter_has_enough_space() {
        when(shelterRepository.queryForShelterLimits()).thenReturn(shelterLimits(10, 7));
        when(shelterRepository.queryForAvailableAnimals()).thenReturn(availableAnimals(5));

        acceptingAnimal.handle(command());

        verify(animals).save(any(Animal.class));
        verify(animals).publish(any(AcceptingAnimalSucceeded.class));
    }

    @DisplayName("Should accept animal with warning when shelter space reached safe threshold")
    @Test
    void should_accept_animal_with_warning_when_shelter_space_reached_safe_threshold() {
        when(shelterRepository.queryForShelterLimits()).thenReturn(shelterLimits(10, 7));
        when(shelterRepository.queryForAvailableAnimals()).thenReturn(availableAnimals(6));

        acceptingAnimal.handle(command());

        verify(animals).save(any(Animal.class));
        verify(animals).publish(any(AcceptingAnimalWarned.class));
    }

    @DisplayName("Should fail when accepting animal because of running out of space")
    @Test
    void should_fail_when_accepting_animal_because_of_running_out_of_space() {
        when(shelterRepository.queryForShelterLimits()).thenReturn(shelterLimits(10, 7));
        when(shelterRepository.queryForAvailableAnimals()).thenReturn(availableAnimals(10));

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