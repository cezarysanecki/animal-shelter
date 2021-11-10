package pl.devcezz.animalshelter.notification.dto;

import java.util.UUID;

public interface Notification {

    enum NotificationType {
        Adoption, SuccessfulAcceptance, WarnedAcceptance, FailureAcceptance
    }

    NotificationType type();

    record SuccessfulAdoptionNotification(UUID animalId, String animalName,
                                          Integer animalAge, String animalSpecies) implements Notification {

        @Override
        public NotificationType type() {
            return NotificationType.Adoption;
        }
    }

    record SuccessfulAcceptanceNotification(UUID animalId, String animalName,
                                            Integer animalAge, String animalSpecies) implements Notification {

        @Override
        public NotificationType type() {
            return NotificationType.SuccessfulAcceptance;
        }
    }

    record WarnedAcceptanceNotification(UUID animalId, String animalName,
                                        Integer animalAge, String animalSpecies,
                                        String message) implements Notification {

        @Override
        public NotificationType type() {
            return NotificationType.WarnedAcceptance;
        }
    }

    record FailureAcceptanceNotification(String reason) implements Notification {

        @Override
        public NotificationType type() {
            return NotificationType.FailureAcceptance;
        }
    }
}