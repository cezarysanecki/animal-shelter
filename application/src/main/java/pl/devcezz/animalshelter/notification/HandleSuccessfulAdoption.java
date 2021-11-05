package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.notification.dto.Notification.SuccessfulAdoptionNotification;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AnimalAdoptionSucceeded;
import pl.devcezz.cqrs.event.EventHandler;

class HandleSuccessfulAdoption implements EventHandler<AnimalAdoptionSucceeded> {

    private final ZookeeperContactRepository zookeeperContactRepository;
    private final Set<Notifier> notifiers;

    HandleSuccessfulAdoption(
            final ZookeeperContactRepository zookeeperContactRepository,
            final Set<Notifier> notifiers
    ) {
        this.zookeeperContactRepository = zookeeperContactRepository;
        this.notifiers = notifiers;
    }

    @Override
    public void handle(final AnimalAdoptionSucceeded event) {
        Set<ZookeeperContact> contacts = zookeeperContactRepository.findAll();

        notifiers.forEach(notifier -> notifier.notify(
                contacts.map(ZookeeperContact::toZookeeperContactDetails),
                createNotification(event))
        );
    }

    private SuccessfulAdoptionNotification createNotification(final AnimalAdoptionSucceeded event) {
        return new SuccessfulAdoptionNotification(
                event.animalId(),
                event.animalName(),
                event.animalAge(),
                event.animalSpecies()
        );
    }
}
