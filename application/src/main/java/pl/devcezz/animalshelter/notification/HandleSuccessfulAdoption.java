package pl.devcezz.animalshelter.notification;

import pl.devcezz.animalshelter.shelter.event.AnimalEvent.AnimalAdoptionSucceeded;
import pl.devcezz.animalshelter.commons.notification.Notification.SuccessfulAdoptionNotification;
import pl.devcezz.animalshelter.commons.notification.Notifier;
import pl.devcezz.cqrs.event.EventHandler;

class HandleSuccessfulAdoption implements EventHandler<AnimalAdoptionSucceeded> {

    private final Notifier notifier;

    HandleSuccessfulAdoption(final Notifier notifier) {
        this.notifier = notifier;
    }

    @Override
    public void handle(final AnimalAdoptionSucceeded event) {
        notifier.notify(new SuccessfulAdoptionNotification(event.getAnimalId()));
    }
}
