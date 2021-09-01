package pl.devcezz.animalshelter.commons.notification;

public interface Notification {

    enum NotificationType {
        Adoption
    }

    NotificationType notificationType();

    record AdoptionNotification(String animalName) implements Notification {

        @Override
        public NotificationType notificationType() {
            return NotificationType.Adoption;
        }
    }
}