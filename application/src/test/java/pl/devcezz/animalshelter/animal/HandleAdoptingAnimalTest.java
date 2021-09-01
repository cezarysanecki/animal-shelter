package pl.devcezz.animalshelter.animal;

import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.animal.event.AnimalEvent.AnimalAdoptionSucceeded;
import pl.devcezz.animalshelter.animal.model.AnimalId;
import pl.devcezz.animalshelter.commons.exception.NotFoundAnimalInShelterException;
import pl.devcezz.animalshelter.commons.notification.Notification.AdoptionNotification;
import pl.devcezz.animalshelter.commons.notification.Notifier;
import pl.devcezz.animalshelter.read.AnimalProjection;
import pl.devcezz.animalshelter.read.query.GetAnimalInfoQuery;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.devcezz.animalshelter.animal.model.AnimalFixture.animalInfo;
import static pl.devcezz.animalshelter.animal.model.AnimalFixture.anyAnimalId;

class HandleAdoptingAnimalTest {

    private final AnimalId animalId = anyAnimalId();

    private Notifier notifier;
    private AnimalProjection projection;

    private HandleAdoptingAnimal handleAdoptingAnimal;

    @BeforeEach
    void setUp() {
        notifier = mock(Notifier.class);
        projection = mock(AnimalProjection.class);

        handleAdoptingAnimal = new HandleAdoptingAnimal(notifier, projection);
    }

    @DisplayName("Should successfully handle adoption event")
    @Test
    void should_successfully_handle_adoption_event() {
        AnimalAdoptionSucceeded event = event(animalId);
        when(projection.handle(query(animalId)))
                .thenReturn(Option.of(animalInfo(animalId.value())));

        handleAdoptingAnimal.handle(event);

        verify(notifier).notify(isA(AdoptionNotification.class));
    }

    @DisplayName("Should fail when animal not found")
    @Test
    void should_fail_when_animal_not_found() {
        AnimalAdoptionSucceeded event = event(animalId);
        when(projection.handle(query(animalId))).thenReturn(Option.none());

        assertThatThrownBy(() -> handleAdoptingAnimal.handle(event))
                .isInstanceOf(NotFoundAnimalInShelterException.class);
    }

    private GetAnimalInfoQuery query(AnimalId animalId) {
        return new GetAnimalInfoQuery(animalId.value());
    }

    private AnimalAdoptionSucceeded event(AnimalId animalId) {
        return AnimalAdoptionSucceeded.adoptingAnimalSucceeded(animalId);
    }
}