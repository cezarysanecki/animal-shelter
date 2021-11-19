package pl.devcezz.animalshelter.notification;

import io.vavr.collection.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent.SuccessfulAnimalAcceptance;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.devcezz.animalshelter.notification.NotificationFixture.zookeeperContact;

class HandleSuccessfulAcceptanceTest {

    private ContactRepository contactRepository;
    private Notifier notifier;

    private HandleSuccessfulAcceptance handleSuccessfulAcceptance;

    @BeforeEach
    void setUp() {
        contactRepository = mock(ContactRepository.class);
        notifier = mock(Notifier.class);

        handleSuccessfulAcceptance = new HandleSuccessfulAcceptance(contactRepository, HashSet.of(notifier));
    }

    @DisplayName("Should successfully notify all notifiers")
    @Test
    void should_successfully_notify_all_notifiers() {
        when(contactRepository.findAll()).thenReturn(HashSet.of(zookeeperContact()));

        handleSuccessfulAcceptance.handle(event());

        verify(notifier).notify(any(), any());
    }

    SuccessfulAnimalAcceptance event() {
        return new SuccessfulAnimalAcceptance(
                UUID.randomUUID(), "Azor", 2, "Dog"
        );
    }
}
