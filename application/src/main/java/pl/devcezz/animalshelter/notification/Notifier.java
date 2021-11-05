package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.notification.dto.Notification;
import pl.devcezz.animalshelter.notification.dto.ZookeeperContactDetails;

interface Notifier {

    void notify(Set<ZookeeperContactDetails> zookeepersContactDetails, Notification notification);
}
