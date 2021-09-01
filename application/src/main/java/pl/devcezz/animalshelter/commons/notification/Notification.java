package pl.devcezz.animalshelter.commons.notification;

import pl.devcezz.animalshelter.animal.model.AnimalId;

public interface Notification {

    enum NotificationType {
        Adoption
    }

    NotificationType type();

    record SuccessfulAdoptionNotification(AnimalId animalId) implements Notification {

        @Override
        public NotificationType type() {
            return NotificationType.Adoption;
        }
    }
}