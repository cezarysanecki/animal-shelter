package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.notification.dto.Notification.FailureAcceptanceNotification;
import pl.devcezz.animalshelter.notification.dto.Notification.SuccessfulAcceptanceNotification;
import pl.devcezz.animalshelter.notification.dto.Notification.WarningAcceptanceNotification;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AcceptingAnimalFailed;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AcceptingAnimalSucceeded;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AcceptingAnimalWarned;
import pl.devcezz.cqrs.event.EventHandler;

class HandleSuccessfulAcceptance implements EventHandler<AcceptingAnimalSucceeded> {

    private final ZookeeperContactRepository zookeeperContactRepository;
    private final Set<Notifier> notifiers;

    HandleSuccessfulAcceptance(final ZookeeperContactRepository zookeeperContactRepository, final Set<Notifier> notifiers) {
        this.zookeeperContactRepository = zookeeperContactRepository;
        this.notifiers = notifiers;
    }

    @Override
    public void handle(final AcceptingAnimalSucceeded event) {
        Set<ZookeeperContact> contacts = zookeeperContactRepository.findAll();

        notifiers.forEach(notifier -> notifier.notify(
                contacts.map(ZookeeperContact::toZookeeperContactDetails),
                createNotification(event))
        );
    }

    private SuccessfulAcceptanceNotification createNotification(final AcceptingAnimalSucceeded event) {
        return new SuccessfulAcceptanceNotification(
                event.animalId(),
                event.animalName(),
                event.animalAge(),
                event.animalSpecies()
        );
    }
}

class HandleAcceptanceWarning implements EventHandler<AcceptingAnimalWarned> {

    private final ZookeeperContactRepository zookeeperContactRepository;
    private final Set<Notifier> notifiers;

    HandleAcceptanceWarning(final ZookeeperContactRepository zookeeperContactRepository, final Set<Notifier> notifiers) {
        this.zookeeperContactRepository = zookeeperContactRepository;
        this.notifiers = notifiers;
    }

    @Override
    public void handle(final AcceptingAnimalWarned event) {
        Set<ZookeeperContact> contacts = zookeeperContactRepository.findAll();

        notifiers.forEach(notifier -> notifier.notify(
                contacts.map(ZookeeperContact::toZookeeperContactDetails),
                createNotification(event))
        );
    }

    private WarningAcceptanceNotification createNotification(final AcceptingAnimalWarned event) {
        return new WarningAcceptanceNotification(
                event.animalId(),
                event.animalName(),
                event.animalAge(),
                event.animalSpecies()
        );
    }
}

class HandleAcceptanceFailure implements EventHandler<AcceptingAnimalFailed> {

    private final ZookeeperContactRepository zookeeperContactRepository;
    private final Set<Notifier> notifiers;

    HandleAcceptanceFailure(final ZookeeperContactRepository zookeeperContactRepository, final Set<Notifier> notifiers) {
        this.zookeeperContactRepository = zookeeperContactRepository;
        this.notifiers = notifiers;
    }

    @Override
    public void handle(final AcceptingAnimalFailed event) {
        Set<ZookeeperContact> contacts = zookeeperContactRepository.findAll();

        notifiers.forEach(notifier -> notifier.notify(
                contacts.map(ZookeeperContact::toZookeeperContactDetails),
                createNotification(event))
        );
    }

    private FailureAcceptanceNotification createNotification(final AcceptingAnimalFailed event) {
        return new FailureAcceptanceNotification(event.reason());
    }
}
