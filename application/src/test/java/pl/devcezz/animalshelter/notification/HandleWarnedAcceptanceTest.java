package pl.devcezz.animalshelter.notification;

import io.vavr.collection.HashSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.WarnedAnimalAcceptance;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static pl.devcezz.animalshelter.notification.NotificationFixture.zookeeperContact;

class HandleWarnedAcceptanceTest {

    private ZookeeperContactRepository zookeeperContactRepository;
    private Notifier notifier;

    private HandleWarnedAcceptance handleWarnedAcceptance;

    @BeforeEach
    void setUp() {
        zookeeperContactRepository = mock(ZookeeperContactRepository.class);
        notifier = mock(Notifier.class);

        handleWarnedAcceptance = new HandleWarnedAcceptance(zookeeperContactRepository, HashSet.of(notifier));
    }

    @DisplayName("Should successfully notify all notifiers")
    @Test
    void should_successfully_notify_all_notifiers() {
        when(zookeeperContactRepository.findAll()).thenReturn(HashSet.of(zookeeperContact()));

        handleWarnedAcceptance.handle(event());

        verify(notifier).notify(any(), any());
    }

    WarnedAnimalAcceptance event() {
        return new WarnedAnimalAcceptance(
                UUID.randomUUID(), "Azor", 2, "Dog", "safe threshold reached"
        );
    }
}
