package pl.devcezz.animalshelter.notification.dto;

import java.util.UUID;

public interface Notification {

    enum NotificationType {
        Adoption
    }

    NotificationType type();

    record SuccessfulAdoptionNotification(UUID animalId, String animalName,
                                          Integer animalAge, String animalSpecies) implements Notification {

        @Override
        public NotificationType type() {
            return NotificationType.Adoption;
        }
    }
}