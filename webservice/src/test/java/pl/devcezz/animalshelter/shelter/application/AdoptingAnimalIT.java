package pl.devcezz.animalshelter.shelter.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.devcezz.animalshelter.shelter.application.command.AdoptAnimalCommand;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent;
import pl.devcezz.animalshelter.shelter.application.exception.AnimalAlreadyAdoptedException;
import pl.devcezz.animalshelter.shelter.application.exception.NotFoundAnimalInShelterException;
import pl.devcezz.cqrs.event.EventsBus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static pl.devcezz.animalshelter.shelter.application.AnimalFixture.animal;

class AdoptingAnimalIT extends ShelterBaseIntegrationTest {

    private final AnimalId animalId = AnimalFixture.anyAnimalId();

    @Test
    @Transactional
    @DisplayName("Should adopt animal which is available")
    void should_adopt_animal_which_is_available(
            @Autowired AdoptingAnimal adoptingAnimal,
            @Autowired EventsBus eventsBus,
            @Autowired ShelterDatabaseRepository repository
    ) {
        repository.save(AnimalFixture.animal(animalId));

        adoptingAnimal.handle(AnimalFixture.adoptAnimalCommand(animalId));

        verify(eventsBus).publish(isA(AnimalEvent.SuccessfulAnimalAdoption.class));
        assertThat(repository.queryForAvailableAnimals()).isEmpty();
    }

    @Test
    @Transactional
    @DisplayName("Should fail when adopting animal which is not in shelter")
    void should_fail_when_adopting_animal_which_is_not_in_shelter(
            @Autowired AdoptingAnimal adoptingAnimal,
            @Autowired EventsBus eventsBus
    ) {
        assertThatThrownBy(() -> adoptingAnimal.handle(AnimalFixture.adoptAnimalCommand(animalId)))
            .isInstanceOf(NotFoundAnimalInShelterException.class);

        verify(eventsBus, never()).publish(any());
    }

    @Test
    @Transactional
    @DisplayName("Should fail when adopting animal which is already adopted")
    void should_fail_when_adopting_animal_which_is_already_adopted(
            @Autowired AdoptingAnimal adoptingAnimal,
            @Autowired EventsBus eventsBus,
            @Autowired ShelterDatabaseRepository repository
    ) {
        repository.save(AnimalFixture.animal(animalId));
        AdoptAnimalCommand command = AnimalFixture.adoptAnimalCommand(animalId);
        adoptingAnimal.handle(command);

        assertThatThrownBy(() -> adoptingAnimal.handle(command))
                .isInstanceOf(AnimalAlreadyAdoptedException.class);

        verify(eventsBus).publish(isA(AnimalEvent.SuccessfulAnimalAdoption.class));
    }
}