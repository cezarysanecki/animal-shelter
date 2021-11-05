package pl.devcezz.animalshelter.notification;

import io.vavr.collection.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AnimalAdoptionSucceeded;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.devcezz.animalshelter.notification.NotificationFixture.zookeeperContact;

class HandleSuccessfulAdoptionTest {

    private ZookeeperContactRepository zookeeperContactRepository;
    private Notifier notifier;

    private HandleSuccessfulAdoption handleSuccessfulAdoption;

    @BeforeEach
    void setUp() {
        zookeeperContactRepository = mock(ZookeeperContactRepository.class);
        notifier = mock(Notifier.class);

        handleSuccessfulAdoption = new HandleSuccessfulAdoption(zookeeperContactRepository, HashSet.of(notifier));
    }

    @DisplayName("Should successfully notify all notifiers")
    @Test
    void should_successfully_notify_all_notifiers() {
        when(zookeeperContactRepository.findAll()).thenReturn(HashSet.of(zookeeperContact()));

        handleSuccessfulAdoption.handle(event());

        verify(notifier).notify(any(), any());
    }

    AnimalAdoptionSucceeded event() {
        return new AnimalAdoptionSucceeded(UUID.randomUUID(), "Azor", 2, "Dog");
    }
}