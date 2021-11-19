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

    private final ContactRepository contactRepository;
    private final Set<Notifier> notifiers;

    HandleSuccessfulAcceptance(final ContactRepository contactRepository, final Set<Notifier> notifiers) {
        this.contactRepository = contactRepository;
        this.notifiers = notifiers;
    }

    @Override
    public void handle(final SuccessfulAnimalAcceptance event) {
        Set<Contact> contacts = contactRepository.findAll();

        notifiers.forEach(notifier -> notifier.notify(
                contacts.map(Contact::toContactDetails),
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

    private final ContactRepository contactRepository;
    private final Set<Notifier> notifiers;

    HandleWarnedAcceptance(final ContactRepository contactRepository, final Set<Notifier> notifiers) {
        this.contactRepository = contactRepository;
        this.notifiers = notifiers;
    }

    @Override
    public void handle(final WarnedAnimalAcceptance event) {
        Set<Contact> contacts = contactRepository.findAll();

        notifiers.forEach(notifier -> notifier.notify(
                contacts.map(Contact::toContactDetails),
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

    private final ContactRepository contactRepository;
    private final Set<Notifier> notifiers;

    HandleFailedAcceptance(final ContactRepository contactRepository, final Set<Notifier> notifiers) {
        this.contactRepository = contactRepository;
        this.notifiers = notifiers;
    }

    @Override
    public void handle(final FailedAnimalAcceptance event) {
        Set<Contact> contacts = contactRepository.findAll();

        notifiers.forEach(notifier -> notifier.notify(
                contacts.map(Contact::toContactDetails),
                createNotification(event))
        );
    }

    private FailedAcceptanceNotification createNotification(final FailedAnimalAcceptance event) {
        return new FailedAcceptanceNotification(event.reason());
    }
}
