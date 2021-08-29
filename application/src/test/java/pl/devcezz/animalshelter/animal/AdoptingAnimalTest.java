package pl.devcezz.animalshelter.animal;

import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.animal.event.AnimalEvent.AnimalAdoptionSucceeded;
import pl.devcezz.animalshelter.animal.command.AdoptAnimalCommand;
import pl.devcezz.animalshelter.animal.model.AdoptedAnimal;
import pl.devcezz.animalshelter.animal.model.AnimalId;
import pl.devcezz.animalshelter.animal.model.AvailableAnimal;
import pl.devcezz.animalshelter.commons.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.commons.exception.NotFoundAnimalInShelterException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.devcezz.animalshelter.animal.AnimalFixture.anyAnimalId;

class AdoptingAnimalTest {

    private final AnimalId animalId = anyAnimalId();

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
        AvailableAnimal availableAnimal = new AvailableAnimal(animalId);
        when(animals.findBy(animalId)).thenReturn(Option.of(availableAnimal));

        adoptingAnimal.handle(command(animalId));

        verify(animals).adopt(availableAnimal);
        verify(animals).publish(isA(AnimalAdoptionSucceeded.class));
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
        AdoptedAnimal adoptedAnimal = new AdoptedAnimal(animalId);
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