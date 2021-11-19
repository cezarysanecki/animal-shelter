package pl.devcezz.animalshelter.shelter.application;

import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.shelter.application.command.AdoptAnimalCommand;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent;
import pl.devcezz.animalshelter.shelter.application.exception.NotFoundAnimalInShelterException;
import pl.devcezz.animalshelter.shelter.application.exception.AnimalAlreadyAdoptedException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AdoptingAnimalTest {

    private final AnimalId animalId = ShelterFixture.anyAnimalId();
    private final AnimalInformation animalInformation = ShelterFixture.animalInformation();

    private Animals animals;

    private AdoptingAnimal adoptingAnimal;

    @BeforeEach
    void setUp() {
        animals = mock(Animals.class);

        adoptingAnimal = new AdoptingAnimal(animals);
    }

    @DisplayName("Should successfully adopt animal which is in shelter")
    @Test
    void should_successfully_adopt_animal_which_is_in_shelter() {
        ShelterAnimal.AvailableAnimal availableAnimal = new ShelterAnimal.AvailableAnimal(animalId, animalInformation);
        when(animals.findBy(animalId)).thenReturn(Option.of(availableAnimal));

        adoptingAnimal.handle(command(animalId));

        verify(animals).adopt(availableAnimal);
        verify(animals).publish(isA(AnimalEvent.SuccessfulAnimalAdoption.class));
    }

    @DisplayName("Should fail adoption when not found animal in shelter")
    @Test
    void should_fail_adoption_when_not_found_animal_in_shelter() {
        when(animals.findBy(animalId)).thenReturn(Option.none());

        assertThatThrownBy(() -> adoptingAnimal.handle(command(animalId)))
            .isInstanceOf(NotFoundAnimalInShelterException.class);

        verify(animals, never()).adopt(any());
        verify(animals, never()).publish(any());
    }

    @DisplayName("Should fail adoption when animal has been already adopted")
    @Test
    void should_fail_adoption_when_animal_has_been_already_adopted() {
        ShelterAnimal.AdoptedAnimal adoptedAnimal = new ShelterAnimal.AdoptedAnimal(animalId, animalInformation);
        when(animals.findBy(animalId)).thenReturn(Option.of(adoptedAnimal));

        assertThatThrownBy(() -> adoptingAnimal.handle(command(animalId)))
                .isInstanceOf(AnimalAlreadyAdoptedException.class);

        verify(animals, never()).adopt(any());
        verify(animals, never()).publish(any());
    }

    private AdoptAnimalCommand command(AnimalId animalId) {
        return new AdoptAnimalCommand(animalId.value());
    }
}