package pl.devcezz.animalshelter.notification;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.commons.notification.Notification.SuccessfulAdoptionNotification;
import pl.devcezz.animalshelter.commons.notification.Notifier;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AnimalAdoptionSucceeded;
import pl.devcezz.animalshelter.shelter.model.AnimalId;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class HandleSuccessfulAdoptionTest {

    private final AnimalId animalId = new AnimalId(UUID.randomUUID());

    private Notifier notifier;

    private HandleSuccessfulAdoption handleSuccessfulAdoption;

    @BeforeEach
    void setUp() {
        notifier = mock(Notifier.class);

        handleSuccessfulAdoption = new HandleSuccessfulAdoption(notifier);
    }

    @DisplayName("Should successfully handle adoption event")
    @Test
    void should_successfully_handle_adoption_event() {
        AnimalAdoptionSucceeded event = event(animalId);

        handleSuccessfulAdoption.handle(event);

        verify(notifier).notify(isA(SuccessfulAdoptionNotification.class));
    }

    private AnimalAdoptionSucceeded event(AnimalId animalId) {
        return AnimalAdoptionSucceeded.adoptingAnimalSucceeded(animalId);
    }
}