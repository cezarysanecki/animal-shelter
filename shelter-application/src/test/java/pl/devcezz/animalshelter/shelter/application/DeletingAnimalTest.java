package pl.devcezz.animalshelter.shelter.application;

import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.shelter.application.command.DeleteAnimalCommand;
import pl.devcezz.animalshelter.shelter.application.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.shelter.application.exception.NotFoundAnimalInShelterException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeletingAnimalTest {

    private final AnimalId animalId = ShelterFixture.anyAnimalId();
    private final AnimalInformation animalInformation = ShelterFixture.animalInformation();

    private Animals animals;

    private DeletingAnimal deletingAnimal;

    @BeforeEach
    void setUp() {
        animals = mock(Animals.class);

        deletingAnimal = new DeletingAnimal(animals);
    }

    @DisplayName("Should successfully delete animal which is in shelter")
    @Test
    void should_successfully_delete_animal_which_is_in_shelter() {
        ShelterAnimal.AvailableAnimal availableAnimal = new ShelterAnimal.AvailableAnimal(animalId, animalInformation);
        when(animals.findBy(animalId)).thenReturn(Option.of(availableAnimal));

        deletingAnimal.handle(command(animalId));

        verify(animals).delete(animalId);
    }

    @DisplayName("Should fail deleting when not found animal in shelter")
    @Test
    void should_fail_deleting_when_not_found_animal_in_shelter() {
        when(animals.findBy(animalId)).thenReturn(Option.none());

        assertThatThrownBy(() -> deletingAnimal.handle(command(animalId)))
                .isInstanceOf(NotFoundAnimalInShelterException.class);

        verify(animals, never()).delete(any());
    }

    @DisplayName("Should fail deleting when animal has been already adopted")
    @Test
    void should_fail_deleting_when_animal_has_been_already_adopted() {
        ShelterAnimal.AdoptedAnimal adoptedAnimal = new ShelterAnimal.AdoptedAnimal(animalId, animalInformation);
        when(animals.findBy(animalId)).thenReturn(Option.of(adoptedAnimal));

        assertThatThrownBy(() -> deletingAnimal.handle(command(animalId)))
                .isInstanceOf(AnimalAlreadyAdoptedException.class);

        verify(animals, never()).delete(any());
    }

    private DeleteAnimalCommand command(AnimalId animalId) {
        return new DeleteAnimalCommand(animalId.value());
    }
}