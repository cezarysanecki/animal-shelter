package pl.devcezz.animalshelter.notification;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import pl.devcezz.animalshelter.notification.dto.Notification.SuccessfulAdoptionNotification;
import pl.devcezz.animalshelter.notification.dto.ZookeeperContactDetails;

import java.util.UUID;

class NotificationFixture {

    static Set<ZookeeperContactDetails> zookeeperContactsDetails() {
        return HashSet.of(new ZookeeperContactDetails("test@mail.com"));
    }

    static SuccessfulAdoptionNotification successfulAdoptionNotification() {
        return new SuccessfulAdoptionNotification(UUID.randomUUID(), "Azor", 2, "Dog");
    }
}
