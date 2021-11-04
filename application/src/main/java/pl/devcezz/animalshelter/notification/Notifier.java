package pl.devcezz.animalshelter.notification;

import io.vavr.collection.Set;
import pl.devcezz.animalshelter.administration.dto.ZookeeperContact;
import pl.devcezz.animalshelter.notification.dto.Notification;

public interface Notifier {

    void notify(Notification notification, Set<ZookeeperContact> contact);
}
