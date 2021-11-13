package pl.devcezz.animalshelter.shelter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.devcezz.animalshelter.shelter.ShelterAnimal.AvailableAnimal;
import pl.devcezz.animalshelter.shelter.command.DeleteAnimalCommand;
import pl.devcezz.animalshelter.shelter.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.shelter.exception.NotFoundAnimalInShelterException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.animal;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.animalInformation;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.anyAnimalId;
import static pl.devcezz.animalshelter.shelter.AnimalFixture.deleteAnimalCommand;

class DeletingAnimalIT extends ShelterBaseIntegrationTest {

    private final AnimalId animalId = anyAnimalId();
    private final AnimalInformation animalInformation = animalInformation();

    @Test
    @Transactional
    @DisplayName("Should delete animal which is available")
    void should_delete_animal_which_is_available(
            @Autowired DeletingAnimal deletingAnimal,
            @Autowired ShelterDatabaseRepository repository
    ) {
        repository.save(animal(animalId));

        deletingAnimal.handle(deleteAnimalCommand(animalId));

        assertThat(repository.queryForAvailableAnimals()).isEmpty();
    }

    @Test
    @Transactional
    @DisplayName("Should fail when deleting animal which is not in shelter")
    void should_fail_when_deleting_animal_which_is_not_in_shelter(
            @Autowired DeletingAnimal deletingAnimal
    ) {
        DeleteAnimalCommand command = deleteAnimalCommand(animalId);

        assertThatThrownBy(() -> deletingAnimal.handle(command))
            .isInstanceOf(NotFoundAnimalInShelterException.class);
    }

    @Test
    @Transactional
    @DisplayName("Should fail when deleting animal which is already adopted")
    void should_fail_when_deleting_animal_which_is_already_adopted(
            @Autowired DeletingAnimal deletingAnimal,
            @Autowired ShelterDatabaseRepository repository
    ) {
        repository.save(animal(animalId));
        repository.adopt(new AvailableAnimal(animalId, animalInformation));

        assertThatThrownBy(() -> deletingAnimal.handle(deleteAnimalCommand(animalId)))
                .isInstanceOf(AnimalAlreadyAdoptedException.class);
    }
}