package pl.devcezz.animalshelter.shelter.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent;
import pl.devcezz.animalshelter.shelter.application.exception.AcceptingAnimalRejectedException;
import pl.devcezz.cqrs.event.EventsBus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static pl.devcezz.animalshelter.shelter.application.AnimalFixture.acceptAnimalCommand;
import static pl.devcezz.animalshelter.shelter.application.AnimalFixture.animal;

class AcceptingAnimalIT extends ShelterBaseIntegrationTest {

    private final AnimalId animalId = AnimalFixture.anyAnimalId();

    @Test
    @Transactional
    @DisplayName("Should accept animal into shelter without reaching safe threshold")
    void should_accept_animal_into_shelter_without_reaching_safe_threshold(
            @Autowired AcceptingAnimal acceptingAnimal,
            @Autowired EventsBus eventsBus,
            @Autowired ShelterDatabaseRepository repository
    ) {
        acceptingAnimal.handle(AnimalFixture.acceptAnimalCommand(animalId.value()));

        verify(eventsBus).publish(isA(AnimalEvent.SuccessfulAnimalAcceptance.class));
        Assertions.assertThat(repository.queryForAvailableAnimals())
                .hasSize(1)
                .allSatisfy(animal -> Assertions.assertThat(animal.animalId()).isEqualTo(animalId));
    }

    @Test
    @Transactional
    @DisplayName("Should accept animal into shelter reaching safe threshold")
    void should_accept_animal_into_shelter_reaching_safe_threshold(
            @Autowired AcceptingAnimal acceptingAnimal,
            @Autowired EventsBus eventsBus,
            @Autowired ShelterDatabaseRepository repository
    ) {
        addAnimalToShelter(repository);

        acceptingAnimal.handle(AnimalFixture.acceptAnimalCommand(animalId.value()));

        verify(eventsBus).publish(isA(AnimalEvent.WarnedAnimalAcceptance.class));
        Assertions.assertThat(repository.queryForAvailableAnimals())
                .hasSize(2)
                .anySatisfy(animal -> Assertions.assertThat(animal.animalId()).isEqualTo(animalId));
    }

    @Test
    @Transactional
    @DisplayName("Should not accept animal into shelter because of reaching limit")
    void should_not_accept_animal_into_shelter_because_of_reaching_limit(
            @Autowired AcceptingAnimal acceptingAnimal,
            @Autowired EventsBus eventsBus,
            @Autowired ShelterDatabaseRepository repository
    ) {
        addAnimalToShelter(repository);
        addAnimalToShelter(repository);

        assertThatThrownBy(() -> acceptingAnimal.handle(AnimalFixture.acceptAnimalCommand(animalId.value())))
                .isInstanceOf(AcceptingAnimalRejectedException.class);

        verify(eventsBus).publish(isA(AnimalEvent.FailedAnimalAcceptance.class));
        Assertions.assertThat(repository.queryForAvailableAnimals())
                .hasSize(2)
                .noneSatisfy(animal -> Assertions.assertThat(animal.animalId()).isEqualTo(animalId));
    }

    private void addAnimalToShelter(ShelterDatabaseRepository repository) {
        repository.save(AnimalFixture.animal());
    }
}