package pl.devcezz.animalshelter.notification;

import io.vavr.collection.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent.SuccessfulAnimalAdoption;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.devcezz.animalshelter.notification.NotificationFixture.zookeeperContact;

class HandleSuccessfulAdoptionTest {

    private ContactRepository contactRepository;
    private Notifier notifier;

    private HandleSuccessfulAdoption handleSuccessfulAdoption;

    @BeforeEach
    void setUp() {
        contactRepository = mock(ContactRepository.class);
        notifier = mock(Notifier.class);

        handleSuccessfulAdoption = new HandleSuccessfulAdoption(contactRepository, HashSet.of(notifier));
    }

    @DisplayName("Should successfully notify all notifiers")
    @Test
    void should_successfully_notify_all_notifiers() {
        when(contactRepository.findAll()).thenReturn(HashSet.of(zookeeperContact()));

        handleSuccessfulAdoption.handle(event());

        verify(notifier).notify(any(), any());
    }

    SuccessfulAnimalAdoption event() {
        return new SuccessfulAnimalAdoption(UUID.randomUUID(), "Azor", 2, "Dog");
    }
}