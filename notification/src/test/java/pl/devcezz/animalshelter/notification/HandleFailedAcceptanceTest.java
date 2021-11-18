package pl.devcezz.animalshelter.notification;

import io.vavr.collection.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent.FailedAnimalAcceptance;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.devcezz.animalshelter.notification.NotificationFixture.zookeeperContact;

class HandleFailedAcceptanceTest {

    private ContactRepository contactRepository;
    private Notifier notifier;

    private HandleFailedAcceptance handleFailedAcceptance;

    @BeforeEach
    void setUp() {
        contactRepository = mock(ContactRepository.class);
        notifier = mock(Notifier.class);

        handleFailedAcceptance = new HandleFailedAcceptance(contactRepository, HashSet.of(notifier));
    }

    @DisplayName("Should successfully notify all notifiers")
    @Test
    void should_successfully_notify_all_notifiers() {
        when(contactRepository.findAll()).thenReturn(HashSet.of(zookeeperContact()));

        handleFailedAcceptance.handle(event());

        verify(notifier).notify(any(), any());
    }

    FailedAnimalAcceptance event() {
        return new FailedAnimalAcceptance("not enough space in shelter");
    }
}
