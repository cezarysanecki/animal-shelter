package pl.devcezz.animalshelter.animal;

import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.animal.command.EditAnimalCommand;
import pl.devcezz.animalshelter.animal.model.AdoptedAnimal;
import pl.devcezz.animalshelter.animal.model.Animal;
import pl.devcezz.animalshelter.animal.model.AnimalId;
import pl.devcezz.animalshelter.animal.model.AvailableAnimal;
import pl.devcezz.animalshelter.commons.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.commons.exception.NotFoundAnimalInShelterException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.devcezz.animalshelter.animal.model.AnimalFixture.anyAnimalId;

class EditingAnimalTest {

    private final AnimalId animalId = anyAnimalId();

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
        EditAnimalCommand command = command(animalId);
        when(animals.findBy(animalId)).thenReturn(Option.of(new AvailableAnimal(animalId)));

        editingAnimal.handle(command);

        verify(animals).update(any(Animal.class));
    }

    @DisplayName("Should fail when editing animal which is adopted")
    @Test
    void should_fail_when_editing_animal_which_is_adopted() {
        EditAnimalCommand command = command(animalId);
        when(animals.findBy(animalId)).thenReturn(Option.of(new AdoptedAnimal(animalId)));

        assertThatThrownBy(() -> editingAnimal.handle(command))
            .isInstanceOf(AnimalAlreadyAdoptedException.class);

        verify(animals, never()).update(any(Animal.class));
    }

    @DisplayName("Should fail when editing animal which is not found")
    @Test
    void should_fail_when_editing_animal_which_is_not_found() {
        EditAnimalCommand command = command(animalId);
        when(animals.findBy(animalId)).thenReturn(Option.none());

        assertThatThrownBy(() -> editingAnimal.handle(command))
                .isInstanceOf(NotFoundAnimalInShelterException.class);

        verify(animals, never()).update(any(Animal.class));
    }

    private EditAnimalCommand command(AnimalId animalId) {
        return new EditAnimalCommand(
                animalId.value(),
                "Azor",
                "Dog",
                2
        );
    }
}