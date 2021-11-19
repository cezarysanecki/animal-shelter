package pl.devcezz.animalshelter.notification;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import pl.devcezz.animalshelter.notification.dto.Notification.SuccessfulAcceptanceNotification;
import pl.devcezz.animalshelter.notification.dto.Notification.WarnedAcceptanceNotification;
import pl.devcezz.animalshelter.notification.dto.Notification.FailedAcceptanceNotification;
import pl.devcezz.animalshelter.notification.dto.Notification.SuccessfulAdoptionNotification;
import pl.devcezz.animalshelter.notification.dto.ContactDetails;

import java.util.UUID;

class NotificationFixture {

    static Set<ContactDetails> zookeeperContactsDetails() {
        return HashSet.of(new ContactDetails("test@mail.com"));
    }

    static SuccessfulAdoptionNotification successfulAdoptionNotification() {
        return new SuccessfulAdoptionNotification(UUID.randomUUID(), "Azor", 2, "Dog");
    }

    static SuccessfulAcceptanceNotification successfulAcceptanceNotification() {
        return new SuccessfulAcceptanceNotification(UUID.randomUUID(), "Azor", 2, "Dog");
    }

    static WarnedAcceptanceNotification warnedAcceptanceNotification() {
        return new WarnedAcceptanceNotification(UUID.randomUUID(), "Azor", 2, "Dog", "safe threshold is reached");
    }

    static FailedAcceptanceNotification failedAcceptanceNotification() {
        return new FailedAcceptanceNotification("not enough space in shelter");
    }
}
