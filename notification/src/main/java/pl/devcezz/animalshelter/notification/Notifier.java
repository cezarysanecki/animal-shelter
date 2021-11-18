package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.notification.dto.Notification;
import pl.devcezz.animalshelter.notification.dto.ContactDetails;

interface Notifier {

    void notify(Set<ContactDetails> zookeepersContactDetails, Notification notification);
}
