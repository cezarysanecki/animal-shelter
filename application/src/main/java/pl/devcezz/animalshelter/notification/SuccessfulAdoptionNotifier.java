package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.administration.AdministrationFacade;
import pl.devcezz.animalshelter.administration.dto.ZookeeperContact;
import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AnimalAdoptionSucceeded;
import pl.devcezz.animalshelter.notification.dto.Notification.SuccessfulAdoptionNotification;
import pl.devcezz.cqrs.event.EventHandler;

class SuccessfulAdoptionNotifier implements EventHandler<AnimalAdoptionSucceeded> {

    private final AdministrationFacade administrationFacade;
    private final Set<Notifier> notifiers;

    SuccessfulAdoptionNotifier(final AdministrationFacade administrationFacade, final Set<Notifier> notifiers) {
        this.administrationFacade = administrationFacade;
        this.notifiers = notifiers;
    }

    @Override
    public void handle(final AnimalAdoptionSucceeded event) {
        Set<ZookeeperContact> zookeeperContacts = administrationFacade.enquireZookeepersContacts();

        notifiers.forEach(notifier -> notifier.notify(new SuccessfulAdoptionNotification(event.animalId()),
                zookeeperContacts));
    }
}
