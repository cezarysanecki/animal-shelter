package pl.devcezz.animalshelter.shelter.application;

import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.shelter.application.command.EditAnimalCommand;
import pl.devcezz.animalshelter.shelter.application.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.shelter.application.exception.NotFoundAnimalInShelterException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EditingAnimalTest {

    private final AnimalId animalId = ShelterFixture.anyAnimalId();
    private final AnimalInformation animalInformation = ShelterFixture.animalInformation();

    private Animals animals;

    private EditingAnimal editingAnimal;

    @BeforeEach
    void setUp() {
        animals = mock(Animals.class);

        editingAnimal = new EditingAnimal(animals);
    }

    @DisplayName("Should successfully edit animal when is in shelter")
    @Test
    void should_successfully_edit_animal_when_is_in_shelter() {
        when(animals.findBy(animalId)).thenReturn(Option.of(new ShelterAnimal.AvailableAnimal(animalId, animalInformation)));

        editingAnimal.handle(command(animalId));

        verify(animals).update(any(Animal.class));
    }

    @DisplayName("Should fail when editing animal which is adopted")
    @Test
    void should_fail_when_editing_animal_which_is_adopted() {
        when(animals.findBy(animalId)).thenReturn(Option.of(new ShelterAnimal.AdoptedAnimal(animalId, animalInformation)));

        assertThatThrownBy(() -> editingAnimal.handle(command(animalId)))
            .isInstanceOf(AnimalAlreadyAdoptedException.class);

        verify(animals, never()).update(any(Animal.class));
    }

    @DisplayName("Should fail when editing animal which is not found")
    @Test
    void should_fail_when_editing_animal_which_is_not_found() {
        when(animals.findBy(animalId)).thenReturn(Option.none());

        assertThatThrownBy(() -> editingAnimal.handle(command(animalId)))
                .isInstanceOf(NotFoundAnimalInShelterException.class);

        verify(animals, never()).update(any(Animal.class));
    }

    private EditAnimalCommand command(AnimalId animalId) {
        return new EditAnimalCommand(
                animalId.value(),
                "Azor",
                "Dog",
                2,
                "Male"
        );
    }
}