package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.notification.dto.Notification.FailedAcceptanceNotification;
import pl.devcezz.animalshelter.notification.dto.Notification.SuccessfulAcceptanceNotification;
import pl.devcezz.animalshelter.notification.dto.Notification.WarnedAcceptanceNotification;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent.FailedAnimalAcceptance;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent.SuccessfulAnimalAcceptance;
import pl.devcezz.animalshelter.shelter.application.event.AnimalEvent.WarnedAnimalAcceptance;
import pl.devcezz.cqrs.event.EventHandler;

class HandleSuccessfulAcceptance implements EventHandler<SuccessfulAnimalAcceptance> {

    private final ZookeeperContactRepository zookeeperContactRepository;
    private final Set<Notifier> notifiers;

    HandleSuccessfulAcceptance(final ZookeeperContactRepository zookeeperContactRepository, final Set<Notifier> notifiers) {
        this.zookeeperContactRepository = zookeeperContactRepository;
        this.notifiers = notifiers;
    }

    @Override
    public void handle(final SuccessfulAnimalAcceptance event) {
        Set<ZookeeperContact> contacts = zookeeperContactRepository.findAll();

        notifiers.forEach(notifier -> notifier.notify(
                contacts.map(ZookeeperContact::toZookeeperContactDetails),
                createNotification(event))
        );
    }

    private SuccessfulAcceptanceNotification createNotification(final SuccessfulAnimalAcceptance event) {
        return new SuccessfulAcceptanceNotification(
                event.animalId(),
                event.animalName(),
                event.animalAge(),
                event.animalSpecies()
        );
    }
}

class HandleWarnedAcceptance implements EventHandler<WarnedAnimalAcceptance> {

    private final ZookeeperContactRepository zookeeperContactRepository;
    private final Set<Notifier> notifiers;

    HandleWarnedAcceptance(final ZookeeperContactRepository zookeeperContactRepository, final Set<Notifier> notifiers) {
        this.zookeeperContactRepository = zookeeperContactRepository;
        this.notifiers = notifiers;
    }

    @Override
    public void handle(final WarnedAnimalAcceptance event) {
        Set<ZookeeperContact> contacts = zookeeperContactRepository.findAll();

        notifiers.forEach(notifier -> notifier.notify(
                contacts.map(ZookeeperContact::toZookeeperContactDetails),
                createNotification(event))
        );
    }

    private WarnedAcceptanceNotification createNotification(final WarnedAnimalAcceptance event) {
        return new WarnedAcceptanceNotification(
                event.animalId(),
                event.animalName(),
                event.animalAge(),
                event.animalSpecies(),
                event.message()
        );
    }
}

class HandleFailedAcceptance implements EventHandler<FailedAnimalAcceptance> {

    private final ZookeeperContactRepository zookeeperContactRepository;
    private final Set<Notifier> notifiers;

    HandleFailedAcceptance(final ZookeeperContactRepository zookeeperContactRepository, final Set<Notifier> notifiers) {
        this.zookeeperContactRepository = zookeeperContactRepository;
        this.notifiers = notifiers;
    }

    @Override
    public void handle(final FailedAnimalAcceptance event) {
        Set<ZookeeperContact> contacts = zookeeperContactRepository.findAll();

        notifiers.forEach(notifier -> notifier.notify(
                contacts.map(ZookeeperContact::toZookeeperContactDetails),
                createNotification(event))
        );
    }

    private FailedAcceptanceNotification createNotification(final FailedAnimalAcceptance event) {
        return new FailedAcceptanceNotification(event.reason());
    }
}
