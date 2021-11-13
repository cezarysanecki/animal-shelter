package pl.devcezz.animalshelter.shelter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AvailableAnimal;
import pl.devcezz.animalshelter.shelter.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.shelter.exception.NotFoundAnimalInShelterException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.animal;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.animalInformation;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.anyAnimalId;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.editAnimalCommand;

class EditingAnimalIT extends ShelterBaseIntegrationTest {

    private final AnimalId animalId = anyAnimalId();
    private final AnimalInformation animalInformation = animalInformation();

    @Test
    @Transactional
    @DisplayName("Should edit animal which is available")
    void should_edit_animal_which_is_available(
            @Autowired EditingAnimal editingAnimal,
            @Autowired ShelterDatabaseRepository repository
    ) {
        Animal availableAnimal = animal(animalId);
        repository.save(availableAnimal);

        editingAnimal.handle(editAnimalCommand(availableAnimal, "Reksio"));

        assertThat(repository.queryForAvailableAnimals()).allSatisfy(animal -> {
            assertThat(animal.getAnimalInformation().name()).isEqualTo("Reksio");
        });
    }

    @Test
    @Transactional
    @DisplayName("Should fail when editing animal which is not in shelter")
    void should_fail_when_editing_animal_which_is_not_in_shelter(
            @Autowired EditingAnimal editingAnimal
    ) {
        Animal notExistingAnimal = animal();

        assertThatThrownBy(() -> editingAnimal.handle(editAnimalCommand(notExistingAnimal, "Reksio")))
            .isInstanceOf(NotFoundAnimalInShelterException.class);
    }

    @Test
    @Transactional
    @DisplayName("Should fail when editing animal which is already adopted")
    void should_fail_when_editing_animal_which_is_already_adopted(
            @Autowired EditingAnimal editingAnimal,
            @Autowired ShelterDatabaseRepository repository
    ) {
        Animal adoptedAnimal = animal(animalId);
        repository.save(adoptedAnimal);
        repository.adopt(new AvailableAnimal(animalId, animalInformation));

        assertThatThrownBy(() -> editingAnimal.handle(editAnimalCommand(adoptedAnimal, "Reksio")))
                .isInstanceOf(AnimalAlreadyAdoptedException.class);
    }
}